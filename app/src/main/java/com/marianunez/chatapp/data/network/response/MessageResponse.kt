package com.marianunez.chatapp.data.network.response

import java.sql.Timestamp

data class MessageResponse(
    val userId: String = "",
    val username: String = "",
    val text: String = "",
    val timestamp: Timestamp
)