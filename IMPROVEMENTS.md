# ft_hangouts - Code Improvements & Best Practices

## Inhaltsverzeichnis

1. [Architektur-Uebersicht](#architektur-uebersicht)
2. [Kritische Verbesserungen (High Priority)](#kritische-verbesserungen-high-priority)
3. [Mittlere Prioritaet (Medium Priority)](#mittlere-prioritaet-medium-priority)
4. [Niedrige Prioritaet (Low Priority)](#niedrige-prioritaet-low-priority)
5. [Projekt-Anforderungen Checkliste](#projekt-anforderungen-checkliste)

---

## Architektur-Uebersicht

### Aktuelle Struktur

```
app/src/main/java/com/example/ft_hangouts/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   │   ├── ContactDao.kt
│   │   │   └── MessageDao.kt
│   │   ├── AppContainer.kt
│   │   ├── AppDatabase.kt
│   │   ├── ChatContract.kt
│   │   └── CursorExtensions.kt
│   ├── model/
│   │   ├── BottomNavItems.kt
│   │   ├── Contact.kt
│   │   ├── Message.kt
│   │   ├── NavResult.kt
│   │   └── UIResult.kt
│   └── repository/
│       ├── ContactRepository.kt
│       ├── MessageRepository.kt
│       └── SMSRepository.kt
├── ui/
│   ├── addContact/
│   ├── components/
│   ├── contacts/
│   ├── message/
│   ├── navigation/
│   ├── settings/
│   ├── theme/
│   └── updateContact/
├── FtHangouts.kt
└── MainActivity.kt
```

### Architektur-Diagramm

```
┌─────────────────────────────────────────────────────────────────┐
│                         UI Layer                                 │
│  ┌──────────────┐     ┌──────────────┐     ┌──────────────┐     │
│  │   Screens    │ <── │  ViewModels  │ <── │  UI State    │     │
│  │  (Compose)   │     │              │     │  (Sealed)    │     │
│  └──────────────┘     └──────────────┘     └──────────────┘     │
└─────────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────┼───────────────────────────────────┐
│                             ▼          Repository Layer          │
│  ┌──────────────────────────────────────────────────────────────┐
│  │   ContactRepository  │  MessageRepository  │  SMSRepository  │
│  └──────────────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────┼───────────────────────────────────┐
│                             ▼             Data Layer             │
│  ┌──────────────────────────────────────────────────────────────┐
│  │         ContactDao          │          MessageDao            │
│  │                (Raw SQLite mit Cursor)                       │
│  └──────────────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────────┘
```

**Bewertung:** MVVM mit Repository Pattern - solide Architektur fuer die Projektanforderungen!

---

## Kritische Verbesserungen (High Priority)

### 1. Datenbank-Operationen auf Main Thread

**Problem:** Alle Repository-Methoden sind synchron und blockieren den UI-Thread. Das kann zu ANR (Application Not Responding) fuehren.

**Betroffene Dateien:**
- `data/repository/ContactRepository.kt` (Zeilen 30-37, 55-77, 79-105, 107-118)
- `data/repository/MessageRepository.kt` (Zeilen 28-45)
- `data/repository/SMSRepository.kt` (Zeilen 8-17)

**Aktueller Code:**
```kotlin
// ContactRepository.kt:30-37
fun getOwnContact(): UIResult<Contact> {
    return contactDao.getOwnContact().fold(
        onSuccess = { UIResult.Success(it) },
        onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                else -> UIResult.DataBaseError
            }
        }
    )
}
```

**Verbesserter Code:**
```kotlin
suspend fun getOwnContact(): UIResult<Contact> = withContext(Dispatchers.IO) {
    contactDao.getOwnContact().fold(
        onSuccess = { UIResult.Success(it) },
        onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                else -> UIResult.DataBaseError
            }
        }
    )
}
```

**Alle zu aendernden Methoden:**

| Datei | Methode |
|-------|---------|
| `ContactRepository.kt` | `getOwnContact()` |
| `ContactRepository.kt` | `getContactByPhoneNumber()` |
| `ContactRepository.kt` | `createContact()` |
| `ContactRepository.kt` | `updateContact()` |
| `ContactRepository.kt` | `deleteContact()` |
| `MessageRepository.kt` | `createMessage()` |
| `MessageRepository.kt` | `deleteMessage()` |
| `SMSRepository.kt` | `sendSms()` |

---

### 2. ViewModels ueberleben Configuration Changes nicht

**Problem:** ViewModels werden direkt in der Activity instanziiert statt ueber `ViewModelProvider`. Bei Rotation oder Configuration Change gehen alle Daten verloren.

**Betroffene Datei:** `MainActivity.kt` (Zeilen 24-37)

**Aktueller Code:**
```kotlin
private val _navViewModel = NavViewModel()
private val _chatViewModel by lazy { ContactsViewModel(container.contactRepo) }
private val _messageViewModel by lazy {
    MessageViewModel(
        container.messageRepo,
        container.contactRepo,
        container.smsRepository
    )
}
private val _addContactViewModel by lazy { AddContactViewModel(container.contactRepo) }
private val _updateContactViewModel by lazy { UpdateContactViewModel(container.contactRepo) }
private val _settingsViewModel = SettingsViewModel()
```

**Verbesserter Code:**

1. Erstelle eine ViewModelFactory:
```kotlin
// ui/ViewModelFactory.kt
class AppViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NavViewModel::class.java) -> 
                NavViewModel() as T
            modelClass.isAssignableFrom(ContactsViewModel::class.java) -> 
                ContactsViewModel(container.contactRepo) as T
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> 
                MessageViewModel(container.messageRepo, container.contactRepo, container.smsRepository) as T
            modelClass.isAssignableFrom(AddContactViewModel::class.java) -> 
                AddContactViewModel(container.contactRepo) as T
            modelClass.isAssignableFrom(UpdateContactViewModel::class.java) -> 
                UpdateContactViewModel(container.contactRepo) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> 
                SettingsViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
```

2. Verwende in MainActivity:
```kotlin
private val factory by lazy { AppViewModelFactory(container) }

private val navViewModel by viewModels<NavViewModel> { factory }
private val contactsViewModel by viewModels<ContactsViewModel> { factory }
private val messageViewModel by viewModels<MessageViewModel> { factory }
private val addContactViewModel by viewModels<AddContactViewModel> { factory }
private val updateContactViewModel by viewModels<UpdateContactViewModel> { factory }
private val settingsViewModel by viewModels<SettingsViewModel> { factory }
```

---

### 3. Force-Unwrap (`!!`) kann Crashes verursachen

**Problem:** Der `!!` Operator kann zu NullPointerException fuehren wenn der Wert null ist.

**Betroffene Stellen:**

| Datei | Zeile | Code |
|-------|-------|------|
| `ui/contacts/ContactsScreen.kt` | 48 | `item.id!!` |
| `ui/message/MessageViewModel.kt` | 39 | `ownContact.id!!` |
| `ui/message/MessageViewModel.kt` | 74 | `ownContact.id!!` |
| `ui/message/SmsReceiver.kt` | 36 | `ownContact.data.id!!` |
| `ui/message/SmsReceiver.kt` | 49 | `contact.id!!` |
| `ui/components/MessageCard.kt` | 29 | `date!!` |
| `ui/components/PickProfileImage.kt` | 69 | `path!!` |
| `ui/addContact/AddContactViewModel.kt` | 69 | `phoneNumber!!` |

**Verbesserungen:**

```kotlin
// ContactsScreen.kt:48
// Vorher:
onDelete = { viewModel.deleteContact(item.id!!) }

// Nachher:
onDelete = { item.id?.let { viewModel.deleteContact(it) } }
```

```kotlin
// MessageCard.kt:29
// Vorher:
val onlyTime = outputFormat.format(date!!)

// Nachher:
val onlyTime = date?.let { outputFormat.format(it) } ?: messageInfo.createdAt
```

```kotlin
// MessageViewModel.kt:39
// Vorher:
emitAll(messageRepository.getAllMessages(ownContact.id!!, contact.id!!))

// Nachher:
val ownId = ownContact.id ?: return@flow emit(UIResult.NotFound("Own contact has no ID"))
val contactId = contact.id ?: return@flow emit(UIResult.NotFound("Contact has no ID"))
emitAll(messageRepository.getAllMessages(ownId, contactId))
```

---

## Mittlere Prioritaet (Medium Priority)

### 4. Fehler werden verschluckt (Silent Error Handling)

**Problem:** In mehreren ViewModels werden Fehler ignoriert, ohne dem User Feedback zu geben.

**Betroffene Datei:** `ui/message/MessageViewModel.kt` (Zeilen 63, 85-86)

**Aktueller Code:**
```kotlin
is UIResult.Loading -> {}
is UIResult.NotFound -> {}
is UIResult.DataBaseError -> {}
```

**Verbesserter Code:**

1. Definiere einen Send-State:
```kotlin
sealed class SendMessageState {
    object Idle : SendMessageState()
    object Sending : SendMessageState()
    object Success : SendMessageState()
    data class Error(val message: String) : SendMessageState()
}
```

2. Exponiere den State:
```kotlin
private val _sendState = MutableStateFlow<SendMessageState>(SendMessageState.Idle)
val sendState: StateFlow<SendMessageState> = _sendState.asStateFlow()

fun sendMessage(input: String, sendTo: Contact) {
    viewModelScope.launch {
        _sendState.value = SendMessageState.Sending
        
        when (val state = contactRepository.getOwnContact()) {
            is UIResult.Success -> {
                // ... send logic
                _sendState.value = SendMessageState.Success
            }
            is UIResult.NotFound -> {
                _sendState.value = SendMessageState.Error("Eigener Kontakt nicht gefunden")
            }
            is UIResult.DataBaseError -> {
                _sendState.value = SendMessageState.Error("Datenbankfehler")
            }
            is UIResult.Loading -> {}
        }
    }
}
```

3. In der UI anzeigen:
```kotlin
val sendState by viewModel.sendState.collectAsState()

when (sendState) {
    is SendMessageState.Error -> {
        Toast.makeText(context, (sendState as SendMessageState.Error).message, Toast.LENGTH_SHORT).show()
    }
    // ...
}
```

---

### 5. Code-Duplizierung: Datum-Formatierung

**Problem:** Gleicher Datums-Formatierungs-Code in mehreren Dateien.

**Betroffene Dateien:**
- `ui/message/SmsReceiver.kt` (Zeilen 42-46, 60-64, 82-86)
- `ui/message/MessageViewModel.kt` (Zeilen 66-67)
- `ui/addContact/AddContactViewModel.kt` (Zeilen 62-63)
- `data/local/AppDatabase.kt` (Zeilen 71-72)
- `ui/components/MessageCard.kt` (Zeilen 26-29)

**Loesung:** Erstelle eine Utility-Klasse:

```kotlin
// data/util/DateUtils.kt
package com.example.ft_hangouts.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val FULL_FORMAT = "dd.MM.yyyy HH:mm:ss"
    private const val TIME_ONLY_FORMAT = "HH:mm"
    
    private val fullFormatter: SimpleDateFormat
        get() = SimpleDateFormat(FULL_FORMAT, Locale.getDefault())
    
    private val timeOnlyFormatter: SimpleDateFormat
        get() = SimpleDateFormat(TIME_ONLY_FORMAT, Locale.getDefault())
    
    /**
     * Gibt den aktuellen Zeitstempel im Format "dd.MM.yyyy HH:mm:ss" zurueck
     */
    fun now(): String = fullFormatter.format(Date())
    
    /**
     * Formatiert einen Zeitstempel zu nur Zeit "HH:mm"
     * Gibt den Original-String zurueck bei Parsing-Fehlern
     */
    fun formatTimeOnly(timestamp: String): String {
        return try {
            val date = fullFormatter.parse(timestamp)
            date?.let { timeOnlyFormatter.format(it) } ?: timestamp
        } catch (e: Exception) {
            timestamp
        }
    }
    
    /**
     * Parst einen Zeitstempel-String zu einem Date-Objekt
     */
    fun parse(timestamp: String): Date? {
        return try {
            fullFormatter.parse(timestamp)
        } catch (e: Exception) {
            null
        }
    }
}
```

**Verwendung:**
```kotlin
// Vorher
val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
val current = sdf.format(Date())

// Nachher
val current = DateUtils.now()
```

---

### 6. Code-Duplizierung: Contact Avatar Anzeige

**Problem:** Gleiche Logik fuer Avatar-Anzeige in mehreren Composables.

**Betroffene Dateien:**
- `ui/components/ContactCard.kt` (Zeilen 60-78)
- `ui/components/TopBar.kt` (Zeilen 53-86)

**Loesung:** Extrahiere ein wiederverwendbares Composable:

```kotlin
// ui/components/ContactAvatar.kt
package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R

@Composable
fun ContactAvatar(
    profilePicture: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val imageModifier = modifier
        .size(size)
        .clip(CircleShape)
    
    if (!profilePicture.isNullOrEmpty()) {
        Image(
            bitmap = loadStringToBitmap(profilePicture),
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(R.drawable.cr),
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}
```

**Verwendung:**
```kotlin
// Vorher (ContactCard.kt:60-78)
if (!contact.profilePicture.isNullOrEmpty()) {
    Image(
        bitmap = loadStringToBitmap(contact.profilePicture),
        contentDescription = contact.firstName,
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
} else {
    Image(
        painter = painterResource(R.drawable.cr),
        // ...
    )
}

// Nachher
ContactAvatar(
    profilePicture = contact.profilePicture,
    contentDescription = contact.firstName,
    size = 64.dp
)
```

---

### 7. Mutable State direkt exponiert

**Problem:** Form-State in ViewModels ist direkt von aussen aenderbar, was Unidirectional Data Flow verletzt.

**Betroffene Dateien:**
- `ui/addContact/AddContactViewModel.kt` (Zeilen 41-44)
- `ui/updateContact/UpdateContactViewModel.kt` (Zeilen 16-19)

**Aktueller Code:**
```kotlin
var firstName by mutableStateOf<String?>(null)
var lastName by mutableStateOf<String?>(null)
var phoneNumber by mutableStateOf<String?>(null)
var profilePic by mutableStateOf<String?>(null)
```

**Verbesserter Code:**
```kotlin
data class ContactFormState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val profilePic: String? = null
)

class AddContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    private var _formState by mutableStateOf(ContactFormState())
    val formState: ContactFormState get() = _formState
    
    fun onFirstNameChange(value: String) {
        _formState = _formState.copy(firstName = value)
    }
    
    fun onLastNameChange(value: String) {
        _formState = _formState.copy(lastName = value)
    }
    
    fun onPhoneNumberChange(value: String) {
        _formState = _formState.copy(phoneNumber = value)
    }
    
    fun onProfilePicChange(value: String?) {
        _formState = _formState.copy(profilePic = value)
    }
    
    fun resetForm() {
        _formState = ContactFormState()
    }
}
```

---

### 8. FileOutputStream nicht korrekt geschlossen

**Problem:** Bei einer Exception zwischen Oeffnen und Schliessen bleibt der Stream offen (Resource Leak).

**Betroffene Datei:** `ui/components/PickProfileImage.kt` (Zeilen 87-91)

**Aktueller Code:**
```kotlin
fun saveProfileImage(context: Context, bitmap: Bitmap): String {
    val filename = "profile_${System.currentTimeMillis()}.png"
    val file = File(context.filesDir, filename)
    val stream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()
    return file.absolutePath
}
```

**Verbesserter Code:**
```kotlin
fun saveProfileImage(context: Context, bitmap: Bitmap): String {
    val filename = "profile_${System.currentTimeMillis()}.png"
    val file = File(context.filesDir, filename)
    
    FileOutputStream(file).use { stream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    }
    
    return file.absolutePath
}
```

---

### 9. SimpleDateFormat bei jeder Recomposition erstellt

**Problem:** In `MessageCard.kt` werden teure Objekte bei jeder Recomposition neu erstellt.

**Betroffene Datei:** `ui/components/MessageCard.kt` (Zeilen 26-29)

**Aktueller Code:**
```kotlin
@Composable
fun MessageCard(modifier: Modifier = Modifier, messageInfo: Message, contact: Contact) {
    Row(...) {
        val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(messageInfo.createdAt)
        val onlyTime = outputFormat.format(date!!)
        // ...
    }
}
```

**Verbesserter Code:**
```kotlin
@Composable
fun MessageCard(modifier: Modifier = Modifier, messageInfo: Message, contact: Contact) {
    val formattedTime = remember(messageInfo.createdAt) {
        DateUtils.formatTimeOnly(messageInfo.createdAt)
    }
    
    Row(...) {
        // Verwende formattedTime direkt
    }
}
```

---

### 10. SMS Fehler nicht propagiert

**Problem:** SMS-Versandfehler werden nur geloggt, nicht an den User weitergegeben.

**Betroffene Datei:** `data/repository/SMSRepository.kt` (Zeilen 8-17)

**Aktueller Code:**
```kotlin
fun sendSms(phoneNumber: String, msg: String) {
    try {
        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null)
        Log.d("test", "Message sent")
    } catch (e: Exception) {
        Log.d("test", "error ${e.message}")
    }
}
```

**Verbesserter Code:**
```kotlin
suspend fun sendSms(phoneNumber: String, msg: String): Result<Unit> = withContext(Dispatchers.IO) {
    runCatching {
        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null)
    }
}
```

---

## Niedrige Prioritaet (Low Priority)

### 11. Debug-Logs entfernen

**Problem:** Debug-Logs mit "test" Tag sollten vor Release entfernt werden.

**Betroffene Stellen:**

| Datei | Zeile |
|-------|-------|
| `data/local/dao/ContactDao.kt` | 30, 33 |
| `ui/message/MessageViewModel.kt` | 69 |
| `data/repository/SMSRepository.kt` | 13, 15 |
| `data/local/AppDatabase.kt` | 80 |
| `ui/navigation/NavViewModel.kt` | 33 |

**Loesung:** Alle `Log.d("test", ...)` Aufrufe entfernen oder durch sinnvolle Tags ersetzen:

```kotlin
companion object {
    private const val TAG = "ContactDao"
}

// Dann verwenden:
Log.d(TAG, "Relevante Debug-Nachricht")
```

---

### 12. Typo in deutscher Uebersetzung

**Betroffene Datei:** `res/values-de/strings.xml` (Zeile 19)

**Aktueller Code:**
```xml
<string name="update_contact_button">Kontakt aktualisierien</string>
```

**Korrektur:**
```xml
<string name="update_contact_button">Kontakt aktualisieren</string>
```

---

### 13. Inkonsistente Namensgebung

| Problem | Datei | Zeile | Empfehlung |
|---------|-------|-------|------------|
| `_chatViewModel` ist ein `ContactsViewModel` | `MainActivity.kt` | 25 | Umbenennen zu `contactsViewModel` |
| Variable heisst `test` statt beschreibend | `AddContactViewModel.kt` | 50 | Umbenennen zu `formErrors` oder `validationErrors` |
| `NavResult` klingt nach Ergebnis, nicht Destination | `NavResult.kt` | - | Umbenennen zu `Screen` oder `Destination` |
| Underscore-Prefix bei Nicht-Backing-Properties | `MainActivity.kt` | 24-37 | Underscore nur fuer Backing Properties |

---

### 14. Theme-Farbe wird nicht persistiert

**Problem:** Die ausgewaehlte Theme-Farbe geht beim App-Neustart verloren.

**Betroffene Datei:** `ui/settings/SettingsViewModel.kt`

**Aktueller Code:**
```kotlin
class SettingsViewModel : ViewModel() {
    var color by mutableStateOf(Color.Black)
        private set

    fun changeThemeColor(color: Color) {
        this.color = color
    }
}
```

**Verbesserter Code:**
```kotlin
class SettingsViewModel(private val prefs: SharedPreferences) : ViewModel() {
    var color by mutableStateOf(loadSavedColor())
        private set
    
    private fun loadSavedColor(): Color {
        val colorInt = prefs.getInt(KEY_THEME_COLOR, Color.Black.toArgb())
        return Color(colorInt)
    }
    
    fun changeThemeColor(newColor: Color) {
        color = newColor
        prefs.edit().putInt(KEY_THEME_COLOR, newColor.toArgb()).apply()
    }
    
    companion object {
        private const val KEY_THEME_COLOR = "theme_color"
    }
}
```

---

### 15. Fehlende Input-Validierung in UpdateContactViewModel

**Problem:** Anders als `AddContactViewModel` validiert `UpdateContactViewModel` keine Eingaben.

**Betroffene Datei:** `ui/updateContact/UpdateContactViewModel.kt`

**Empfehlung:** Dieselbe Validierungslogik wie in `AddContactViewModel` hinzufuegen oder gemeinsame Validierungs-Utility erstellen.

---

## Projekt-Anforderungen Checkliste

### Mandatory Part

| # | Anforderung | Status | Implementierung |
|---|-------------|--------|-----------------|
| 1 | Contact erstellen | ✅ | `AddContactScreen.kt`, `AddContactViewModel.kt` |
| 2 | Contact bearbeiten | ✅ | `UpdateContactScreen.kt`, `UpdateContactViewModel.kt` |
| 3 | Contact loeschen | ✅ | `ContactsViewModel.kt:22-26` |
| 4 | Homepage mit Kontakt-Uebersicht | ✅ | `ContactsScreen.kt` |
| 5 | SMS empfangen | ✅ | `SmsReceiver.kt` |
| 6 | SMS senden | ✅ | `MessageViewModel.kt:58-89` |
| 7 | Konversations-Historie (Sender/Empfaenger) | ✅ | `MessagesScreen.kt` |
| 8 | Menu fuer Header-Farbe | ✅ | `SettingsScreen.kt` |
| 9 | Zwei Sprachen | ✅ | `values/strings.xml`, `values-de/strings.xml` |
| 10 | Zeit im Hintergrund anzeigen | ✅ | `MainActivity.kt:72-88` |
| 11 | Landscape & Portrait Mode | ✅ | Compose handled automatisch |
| 12 | 42 Logo als App-Icon | ⚠️ | Pruefen ob korrekt gesetzt |

### Bonus Part

| # | Anforderung | Status | Implementierung |
|---|-------------|--------|-----------------|
| 1 | Profilbild pro Kontakt | ✅ | `PickProfileImage.kt`, `Contact.profilePicture` |
| 2 | Auto-Kontakt bei SMS von unbekannter Nummer | ✅ | `SmsReceiver.kt:59-77` |
| 3 | Material Design | ✅ | Material3 Compose |
| 4 | Kontakt anrufen | ✅ | `TopBar.kt:112` mit `tel:` Intent |

---

## Zusammenfassung: Prioritaeten-Matrix

| Prioritaet | Aufwand | Beschreibung | Dateien |
|------------|---------|--------------|---------|
| 🔴 HIGH | Mittel | `suspend fun` + `Dispatchers.IO` | Repositories |
| 🔴 HIGH | Niedrig | `ViewModelProvider` mit Factory | `MainActivity.kt` |
| 🔴 HIGH | Niedrig | `!!` durch sichere Checks ersetzen | Diverse |
| 🟡 MED | Niedrig | Error States an UI weitergeben | ViewModels |
| 🟡 MED | Niedrig | `DateUtils` Utility erstellen | Neu |
| 🟡 MED | Niedrig | `ContactAvatar` Composable | Neu |
| 🟡 MED | Niedrig | `FileOutputStream.use {}` | `PickProfileImage.kt` |
| 🟡 MED | Niedrig | SMS Fehler propagieren | `SMSRepository.kt` |
| 🟢 LOW | Trivial | Debug-Logs entfernen | Diverse |
| 🟢 LOW | Trivial | Typo fixen | `strings.xml` |
| 🟢 LOW | Niedrig | Theme persistieren | `SettingsViewModel.kt` |
| 🟢 LOW | Trivial | Naming Conventions | Diverse |

---

## Quick Wins (Sofort umsetzbar)

1. **Typo fixen** in `values-de/strings.xml`
2. **Debug-Logs entfernen** (alle `Log.d("test", ...)`)
3. **`!!` ersetzen** durch `?.let {}` oder `?: return`
4. **`FileOutputStream.use {}`** in `PickProfileImage.kt`

Diese Aenderungen sind minimal-invasiv und verbessern sofort die Code-Qualitaet!
