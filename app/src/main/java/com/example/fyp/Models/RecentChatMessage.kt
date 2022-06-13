package com.example.fyp.Models

import java.io.Serializable

class RecentChatMessage : Serializable {
    var id: String? = null
    var senderId: String? = null
    var receiverId: String? = null
    var senderName: String? = null
    var receiverName: String? = null
    var message: String? = null


    constructor() {}

    constructor(
        id: String?,
        senderId: String?,
        receiverId: String?,
        senderName: String?,
        receiverName: String?,
        message: String?
    ) {
        this.id = id
        this.senderId = senderId
        this.receiverId = receiverId
        this.senderName = senderName
        this.receiverName = receiverName
        this.message = message
    }

    override fun equals(other: Any?): Boolean {
        val recentChatMessage1=other as RecentChatMessage
        if (id == null || recentChatMessage1.id == null)
            return false
        if (id == recentChatMessage1.id) return true
        return false
    }
}