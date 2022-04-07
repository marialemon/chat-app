package com.marianunez.chatapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.chatapp.databinding.ActivityChatBinding
import com.marianunez.chatapp.fragments.CreateChatDialogFragment

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var createChatDialog: CreateChatDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize the lateinit var
        createChatDialog = CreateChatDialogFragment()

        initLayout()
    }


    private fun initLayout() {
        binding.noChats.visibility = View.VISIBLE

        recyclerView = binding.chatList
        recyclerView.layoutManager = LinearLayoutManager(this)


        binding.createChat.setOnClickListener{
            showChatDialog()
        }
    }

    private fun showChatDialog(){
        createChatDialog.show(supportFragmentManager, CreateChatDialogFragment.TAG)
    }

}