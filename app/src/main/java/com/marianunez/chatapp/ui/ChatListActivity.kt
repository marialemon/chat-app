package com.marianunez.chatapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.marianunez.chatapp.R
import com.marianunez.chatapp.commons.startActivity
import com.marianunez.chatapp.commons.startActivityWithExtras
import com.marianunez.chatapp.data.network.response.ChatResponse
import com.marianunez.chatapp.databinding.ActivityChatListBinding
import com.marianunez.chatapp.ui.adapters.ChatListAdapter
import com.marianunez.chatapp.ui.fragments.CreateChatDialogFragment

class ChatListActivity : AppCompatActivity(), ChatListAdapter.OnChatSelectedListener {

    private lateinit var createChatDialog: CreateChatDialogFragment
    private lateinit var binding: ActivityChatListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatListAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var query: Query
    private lateinit var chatReference: DocumentReference

    companion object {
        private const val CHATS = "chats"
        private const val NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        auth = FirebaseAuth.getInstance()
        createChatDialog = CreateChatDialogFragment()  //initialize the lateinit var

        initFirestore()
        initLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {
                auth.signOut()
                startActivity<LoginActivity>()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChatSelected(chat: ChatResponse?) {
        chat?.let {
            startActivityWithExtras<MessageListActivity> {
                putExtra(MessageListActivity.KEY_CHAT_ID, it.id)
                putExtra(MessageListActivity.KEY_CHAT_NAME, it.name)
            }
        }
    }

    private fun showChatDialog() {
        createChatDialog.show(supportFragmentManager, CreateChatDialogFragment.TAG)
    }

    private fun initFirestore() {
        firestore = FirebaseFirestore.getInstance()  //initialize the lateinit var
        chatReference =
            firestore.collection(CHATS).document() //CHATS is declared in the companion object
        query = firestore.collection(CHATS).orderBy(NAME, Query.Direction.DESCENDING)
    }

    private fun initLayout() {
        recyclerView = binding.chatList
        recyclerView.layoutManager = LinearLayoutManager(this@ChatListActivity)

        adapter = object : ChatListAdapter(query, this@ChatListActivity) {
            override fun onError(e: FirebaseFirestoreException?) {
                println("***************************** error")
            }

            override fun onDataChanged() {
                if (itemCount == 0) {
                    binding.noChats.visibility = View.VISIBLE
                } else {
                    binding.noChats.visibility = View.GONE
                }
                recyclerView.adapter = adapter
            }
        }

        binding.createChat.setOnClickListener {
            showChatDialog()
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

}