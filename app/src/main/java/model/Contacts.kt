package model

import android.graphics.Picture

data class Contacts(
    val id: String,
    var name: String,
    val number: Int,
    var profilePicture: Picture
)
