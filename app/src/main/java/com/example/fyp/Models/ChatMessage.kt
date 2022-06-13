package com.example.fyp.Models

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ChatMessage : Serializable {
    var senderId: String? = null
    var receiverId: String? = null
    var message: String? = null
    var dateTime: Date? = null

    constructor(senderId: String?, receiverId: String?, message: String?, dateTime: Date?) {
        this.senderId = senderId
        this.receiverId = receiverId
        this.message = message
        this.dateTime = dateTime
    }

    constructor() {}

    val simpleDateTime: String
        get() = SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(
            dateTime
        )
}
