package com.example.fyp.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.databinding.ItemContainerReceivedMessageBinding
import com.example.fyp.databinding.ItemContainerSentMessageBinding
import com.example.fyp.Models.ChatMessage


class ChatAdapter(
    private var chatMessages: MutableList<ChatMessage>,
    private val senderId: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val itemContainerSentMessageBinding: ItemContainerSentMessageBinding =
                ItemContainerSentMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            SentMessageViewHolder(itemContainerSentMessageBinding)
        } else {
            val itemContainerReceivedMessageBinding: ItemContainerReceivedMessageBinding =
                ItemContainerReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ReceiverMessageViewHolder(itemContainerReceivedMessageBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).setData(chatMessages[position])
        } else if (holder.itemViewType == VIEW_TYPE_RECEIVED) {
            (holder as ReceiverMessageViewHolder).setData(chatMessages[position])
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].senderId.equals(senderId)) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    inner class SentMessageViewHolder(var binding: ItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.simpleDateTime
        }
    }

    inner class ReceiverMessageViewHolder(var binding: ItemContainerReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.simpleDateTime
        }
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }
}