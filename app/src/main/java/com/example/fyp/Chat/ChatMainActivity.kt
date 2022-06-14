package com.example.fyp.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Adapters.RecentChatAdapter
import com.example.fyp.R
import com.example.fyp.databinding.ActivityChatMainBinding
import com.example.fyp.Models.RecentChatMessage
import com.example.fyp.Utilities.Constants
import com.example.fyp.Utilities.PreferenceManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ChatMainActivity : Fragment() {
    private lateinit var binding: ActivityChatMainBinding
    private lateinit var preferenceManager: PreferenceManager
    private val recentChatList = mutableListOf<RecentChatMessage>()
    private lateinit var recentChatAdapter: RecentChatAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_chat_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityChatMainBinding.bind(requireView())

        preferenceManager = PreferenceManager(requireContext().applicationContext)
        recentChatAdapter =
            RecentChatAdapter(recentChatList, preferenceManager.getString(Constants.KEY_USER_ID)!!)

        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerView.adapter = recentChatAdapter

        setListeners()
        loadRecentChats()
    }

    private fun setListeners() {
        //binding.arrowBack.setOnClickListener { onBackPressed() }
    }

    private fun loadRecentChats() {
        db.collection(Constants.KEY_COLLECTION_RECENT_CHATS)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener(recentChatQuerySnapshot)
        db.collection(Constants.KEY_COLLECTION_RECENT_CHATS)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener(recentChatQuerySnapshot)
    }

    private val recentChatQuerySnapshot: EventListener<QuerySnapshot> =
        EventListener { value, error ->
            if (error != null)
                return@EventListener
            if (value != null) {
                for (doc in value.documentChanges) {
                    if (doc.type == DocumentChange.Type.ADDED) {
                        try {
                            val recentChat = doc.document.toObject(RecentChatMessage::class.java)
                            recentChatList.add(recentChat)
                            recentChatAdapter.notifyItemInserted(recentChatList.size - 1)
                        } catch (e: Exception) {
                            Log.e("ChatMainActivity", "recentChatQuerySnapshot: ${e.message}")
                        }
                    } else if (doc.type == DocumentChange.Type.MODIFIED) {
                        try {
                            val recentChatMessage =
                                doc.document.toObject(RecentChatMessage::class.java)
                            val index = recentChatList.indexOf(recentChatMessage)
                            recentChatList[index] = recentChatMessage
                            recentChatAdapter.notifyItemChanged(index)
                        } catch (e: Exception) {
                            Log.e("ChatMainActivity", "recentChatQuerySnapshot: ${e.message}")
                        }
                    }
                }
            }
        }
}