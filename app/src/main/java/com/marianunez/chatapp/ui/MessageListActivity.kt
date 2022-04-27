package com.marianunez.chatapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.marianunez.chatapp.data.network.response.MessageResponse
import com.marianunez.chatapp.databinding.ActivityMessageListBinding
import com.marianunez.chatapp.ui.adapters.MessageListAdapter

class MessageListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageListAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var query: Query
    private lateinit var chatReference: DocumentReference

    companion object {
        private const val LIMIT = 50
        const val KEY_CHAT_ID = "key_restaurant_id"
        const val KEY_CHAT_NAME = "key_channel_name"
        private const val MESSAGE_COLLECTION = "messages"
        private const val CHAT_COLLECTION = "chats"
        private const val TIMESTAMP = "timestamp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val chatId = intent.extras?.getString(KEY_CHAT_ID)
            ?: throw IllegalArgumentException("must pass extra $KEY_CHAT_ID")
        title = intent.extras?.getString(KEY_CHAT_NAME)

        initFirestore(chatId)
        initLayout()
    }

    private fun initFirestore(chatId: String) {
        firestore = FirebaseFirestore.getInstance()  //initialize the lateinit var
        chatReference =
            firestore.collection(CHAT_COLLECTION).document(chatId)
        query = chatReference.collection(MESSAGE_COLLECTION)
            .orderBy(TIMESTAMP, Query.Direction.ASCENDING)
    }

    private fun initLayout() {
        recyclerView = binding.messageList
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = object : MessageListAdapter(query) {
            override fun onError(e: FirebaseFirestoreException?) {
                println("***************************** error")
            }

            override fun onDataChanged() {
                if (itemCount == 0) {
                    binding.noMessages.visibility = View.VISIBLE
                } else {
                    binding.noMessages.visibility = View.GONE
                }
                recyclerView.adapter = adapter
            }
        }

        binding.sendButton.setOnClickListener {
            sendClicked(it)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun addMessage(text: String): Task<Void> {
        val chatReference = chatReference.collection(MESSAGE_COLLECTION).document()
        val currentUser = auth.currentUser
        val message =
            MessageResponse(currentUser?.uid ?: "", currentUser?.displayName ?: "", text, null)

        return firestore.runTransaction {
            it[chatReference] = message
            null
        }
    }

    private fun sendClicked(view: View) {
        addMessage(binding.messageField.text.toString())
            .addOnSuccessListener(this) {
                reset()
            }
            .addOnFailureListener(this) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun reset() {
        binding.messageField.setText("")
        val view = currentFocus

        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
        recyclerView.smoothScrollToPosition(recyclerView.adapter!!.itemCount - 1)
        binding.messageField.clearFocus()
    }
}