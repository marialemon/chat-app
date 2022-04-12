package com.marianunez.chatapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.marianunez.chatapp.R
import com.marianunez.chatapp.commons.startActivity
import com.marianunez.chatapp.databinding.ActivityChatBinding
import com.marianunez.chatapp.ui.fragments.CreateChatDialogFragment

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var createChatDialog: CreateChatDialogFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        auth = FirebaseAuth.getInstance()

        //initialize the lateinit var
        createChatDialog = CreateChatDialogFragment()

        initLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {
                startActivity<LoginActivity>()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initLayout() {
        binding.noChats.visibility = View.VISIBLE

        recyclerView = binding.chatList
        recyclerView.layoutManager = LinearLayoutManager(this)


        binding.createChat.setOnClickListener {
            showChatDialog()
        }
    }

    private fun showChatDialog() {
        createChatDialog.show(supportFragmentManager, CreateChatDialogFragment.TAG)
    }

}