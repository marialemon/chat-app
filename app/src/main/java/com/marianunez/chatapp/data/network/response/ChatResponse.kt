package com.marianunez.chatapp.data.network.response

import com.google.firebase.firestore.DocumentId

data class ChatResponse(
    @DocumentId // this tells Firebase that should be filled automatically with the document id when the object is created from a Firebase document
    val id: String = "",
    val name: String = "",
    val description: String = ""
)