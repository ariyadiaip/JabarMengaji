package edu.ariyadi.jabarmengaji.data.model

import com.google.firebase.firestore.DocumentId

data class Community(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val schedule: String = "",
    val base64Image: String = "",
    val urlMaps: String = ""
)