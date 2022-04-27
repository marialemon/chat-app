package com.marianunez.chatapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.marianunez.chatapp.R
import com.marianunez.chatapp.commons.inflate
import com.marianunez.chatapp.data.network.response.ChatResponse
import com.marianunez.chatapp.databinding.ChatItemBinding

open class ChatListAdapter(query: Query, private val listener: OnChatSelectedListener) :
    FirestoreAdapter<ChatListAdapter.ChatListViewHolder>(query) {

    interface OnChatSelectedListener {
        fun onChatSelected(chat: ChatResponse?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val adapterLayout = parent.inflate(R.layout.chat_item)
        return ChatListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        getSnapshot(position).toObject(ChatResponse::class.java)?.let { holder.bind(it, listener) }
    }

    inner class ChatListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ChatItemBinding.bind(view)

        fun bind(chat: ChatResponse, listener: OnChatSelectedListener) = with(binding) {
            chatName.text = chat.name
            chatDescription.text = chat.description

            itemView.setOnClickListener { listener.onChatSelected(chat) }
        }
    }
}
