package com.marianunez.chatapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.marianunez.chatapp.databinding.FragmentMessageListBinding

class MessageListFragment : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentMessageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        binding.noMessages.visibility = View.VISIBLE

        recyclerView = binding.messageList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.sendButton.setOnClickListener { view ->
            Snackbar.make(view, "Message sent", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}