package com.marianunez.chatapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.marianunez.chatapp.databinding.FragmentCreateChatDialogBinding

class CreateChatDialogFragment : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentCreateChatDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        //la TAG es útil para identificar el fragment
        const val TAG = "Create Chat Dialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateChatDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initLayout()
    }

    private fun initLayout(){
        // this is to set the width and height of the dialog
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onClick(v: View) {
        when (v) {
            binding.createButton -> onCreateClicked()
            binding.closeButton -> onCloseClicked()
        }
    }

    private fun onCloseClicked() {
        binding.closeButton.setOnClickListener(this)
        binding.inputChatName.text.clear()
        binding.inputChatDescription.text.clear()
        dismiss()
    }

    private fun onCreateClicked() {
        //TODO
    }

}