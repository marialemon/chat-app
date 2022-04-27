package com.marianunez.chatapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.marianunez.chatapp.R
import com.marianunez.chatapp.commons.inflate
import com.marianunez.chatapp.data.network.response.MessageResponse
import com.marianunez.chatapp.databinding.MessageItemBinding

open class MessageListAdapter(query: Query) :
    FirestoreAdapter<MessageListAdapter.MessageListViewHolder>(query) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
        val adapterLayout = parent.inflate(R.layout.message_item)
        return MessageListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        getSnapshot(position).toObject(MessageResponse::class.java)
            ?.let { holder.bind(it) }
    }

    inner class MessageListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MessageItemBinding.bind(view)

        fun bind(message: MessageResponse) =
            with(binding) {
                userName.text = message.username
                userMessage.text = message.text
                timeSent.text = message.timestamp.toString()
            }
    }
}


