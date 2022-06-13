package com.example.fyp.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Models.UserData
import com.example.fyp.databinding.ItemContainerRecentChatBinding
import com.example.fyp.Chat.ChatActivity
import com.example.fyp.Models.RecentChatMessage
import com.example.fyp.Utilities.Constants
import com.example.fyp.Utilities.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore

class RecentChatAdapter(
    private val recentChatMessageList: MutableList<RecentChatMessage>,
    private var userId: String
) :
    RecyclerView.Adapter<RecentChatAdapter.RecentChatViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatViewHolder {
        val binding = ItemContainerRecentChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentChatViewHolder, position: Int) {
        holder.setData(recentChatMessageList[position])
        holder.itemView.setOnClickListener {
            val recentChatMessage = recentChatMessageList[position]
            val preferenceManager = PreferenceManager(holder.itemView.context.applicationContext)
            val mi = Intent(holder.itemView.context, ChatActivity::class.java)
            mi.putExtra(Constants.RECENT_CHAT_MESSAGE, recentChatMessageList[position])


            if (recentChatMessage.senderId == preferenceManager.getString(Constants.KEY_USER_ID)) {
                db.collection(Constants.KEY_COLLECTION_USER)
                    .document(recentChatMessage.receiverId!!)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            mi.putExtra(Constants.KEY_USER, it.result.toObject(UserData::class.java))
                            holder.itemView.context.startActivity(mi)
                        }
                    }
            } else {
                db.collection(Constants.KEY_COLLECTION_USER)
                    .document(recentChatMessage.senderId!!)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            mi.putExtra(Constants.KEY_USER, it.result.toObject(UserData::class.java))
                            holder.itemView.context.startActivity(mi)
                        }
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.recentChatMessageList.size
    }

    inner class RecentChatViewHolder(private val binding: ItemContainerRecentChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(recentChatMessage: RecentChatMessage) {
            if (userId == recentChatMessage.senderId) {
                binding.textName.text = recentChatMessage.receiverName
            } else if (userId == recentChatMessage.receiverId) {
                binding.textName.text = recentChatMessage.senderName
            }
            binding.textMessage.text = recentChatMessage.message
        }

    }
}