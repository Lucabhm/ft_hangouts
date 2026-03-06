package com.example.ft_hangouts.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PickProfileImage(onUpdate: (String) -> Unit) {
    var path = ""
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(context, uri)
                path = saveProfileImage(context, bitmap)
                onUpdate(path)
            }
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(
                onClick = {
                    launcher.launch("image/*")
                },
            ),
    ) {
        if (path.isEmpty())
            Icon(Icons.Default.Add, "addPic")
        else {
            Image(bitmap = loadStringToBitmap(path), null)
        }
    }

    Text(text = "Add a Picture")
}

@RequiresApi(Build.VERSION_CODES.P)
fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    val src = ImageDecoder.createSource(context.contentResolver, uri)

    return ImageDecoder.decodeBitmap(src)
}

fun saveProfileImage(context: Context, bitmap: Bitmap): String {

    val filename = "profile_${System.currentTimeMillis()}.png"
    val file = File(context.filesDir, filename)
    val stream = FileOutputStream(file)

    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()

    return file.absolutePath
}

fun loadStringToBitmap(path: String): ImageBitmap {
    return BitmapFactory.decodeFile(path).asImageBitmap()
}
