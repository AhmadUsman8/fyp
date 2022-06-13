package com.example.fyp.Chat


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Adapters.ChatAdapter
import com.example.fyp.Models.UserData
import com.example.fyp.databinding.ActivityChatBinding
import com.example.fyp.Models.ChatMessage
import com.example.fyp.Models.RecentChatMessage
import com.example.fyp.Utilities.Constants
import com.example.fyp.Utilities.PreferenceManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

/**
 * Require receiver: User
 * Need recentChatMessage: RecentChatMessage
 */
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var preferenceManager: PreferenceManager
    private var receiver: UserData? = null
    private var chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter
    private var db = FirebaseFirestore.getInstance()
    private var recentChatMessage: RecentChatMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recentChatMessage =
            intent.getSerializableExtra(Constants.RECENT_CHAT_MESSAGE) as RecentChatMessage?

        preferenceManager = PreferenceManager(applicationContext)
        chatAdapter =
            ChatAdapter(chatMessages, preferenceManager.getString(Constants.KEY_USER_ID)!!)
        binding.recyclerChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerChat.adapter = chatAdapter

        loading(true)
        loadReceiverDetails()
        setListeners()
        loadChatMessages()
    }

    private fun loadReceiverDetails() {
        receiver = intent.getSerializableExtra(Constants.KEY_USER) as UserData?
        if (receiver == null) {
            Log.e("ChatActivity", "onCreate: Receiver is null.")
            finish()
        }
        binding.textName.text = receiver!!.fname+" "+receiver!!.lname
    }

    private fun setListeners() {
        binding.imageSend.setOnClickListener { sendMessage() }
        binding.arrowBack.setOnClickListener { onBackPressed() }
    }

    private fun sendMessage() {
        if (binding.inputMessage.text.toString().isEmpty()) {
            binding.inputMessage.error = "Empty message."
            return
        }
        val message = ChatMessage(
            preferenceManager.getString(Constants.KEY_USER_ID),
            receiver!!.id,
            binding.inputMessage.text.toString(),
            Date()
        )
        db.collection(Constants.KEY_COLLECTION_CHAT)
            .add(message)
            .addOnSuccessListener {
                if (recentChatMessage == null) {
                    val docId = db.collection(Constants.KEY_COLLECTION_RECENT_CHATS).document().id
                    recentChatMessage = RecentChatMessage(
                        docId,
                        preferenceManager.getString(Constants.KEY_USER_ID),
                        receiver!!.id,
                        preferenceManager.getString(Constants.KEY_USER_NAME),
                        receiver!!.fname+" "+receiver!!.lname,
                        message.message
                    )
                    db.collection(Constants.KEY_COLLECTION_RECENT_CHATS)
                        .document(docId)
                        .set(recentChatMessage!!)
                } else {
                    recentChatMessage!!.message = message.message
                    db.collection(Constants.KEY_COLLECTION_RECENT_CHATS)
                        .document(recentChatMessage!!.id!!)
                        .update(Constants.KEY_MESSAGE, recentChatMessage!!.message)
                }
            }

        binding.inputMessage.text = null
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.recyclerChat.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.recyclerChat.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    // event listeners
    private val eventListener: EventListener<QuerySnapshot> = EventListener { value, error ->
        if (error != null)
            return@EventListener
        if (value != null) {
            val count = chatMessages.size
            for (doc in value.documentChanges) {
                if (doc.type == DocumentChange.Type.ADDED) {
                    val message: ChatMessage =
                        doc.document.toObject(ChatMessage::class.java)
                    chatMessages.add(message)
                }
            }
            chatMessages.sortBy { chatMessage -> chatMessage.dateTime }

            /*Collections.sort(chatMessages,
                Comparator<T> { m1: T, m2: T ->
                    m1.getDateTime().compareTo(m2.getDateTime())
                })*/

            loading(false)
            if (count == 0) {
                chatAdapter.notifyDataSetChanged()
            } else {
                chatAdapter.notifyItemRangeInserted(count, chatMessages.size - count)
                binding.recyclerChat.smoothScrollToPosition(chatMessages.size - 1)
            }
        }
    }

    private fun loadChatMessages() {
        db.collection(Constants.KEY_COLLECTION_CHAT)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .whereEqualTo(Constants.KEY_RECEIVER_ID, receiver!!.id)
            .addSnapshotListener(eventListener)
        db.collection(Constants.KEY_COLLECTION_CHAT)
            .whereEqualTo(Constants.KEY_SENDER_ID, receiver!!.id)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener(eventListener)
    }
}
