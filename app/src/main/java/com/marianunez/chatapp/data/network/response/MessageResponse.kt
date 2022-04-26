package com.marianunez.chatapp.data.network.response

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageResponse(
    val userId: String = "",
    val username: String = "",
    val text: String = "",
    @ServerTimestamp // automatically filled with the server timestamp
    val timestamp: Date? = null
)