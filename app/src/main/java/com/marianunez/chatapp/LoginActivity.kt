package com.marianunez.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marianunez.chatapp.commons.startActivity
import com.marianunez.chatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth // 1. declare Firebase Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // 2. initialize FirebaseAuth

        initUi()
    }

    private fun initUi() {
        if (auth.currentUser != null) {
            goToChats()
        }

        binding.logInButton.setOnClickListener {
            handleLogIn()
        }

        binding.signUpButton.setOnClickListener {
            startActivity<SignUpActivity>()
        }

        binding.forgotPassword.setOnClickListener{
            resetPassword()
        }
    }

    private fun handleLogIn() {
        when {
            !isEmailValid(binding.emailInput.text.toString()) -> binding.emailLayout.error = "Email not valid"
            !isPasswordValid(binding.passwordInput.text.toString()) -> binding.passwordLayout.error = "Password not valid"
            else -> {
                binding.apply {
                    loading.visibility = View.VISIBLE
                }
                handleAuthentication()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean = email.contains("@") && email.isNotBlank()
    private fun isPasswordValid(password: String): Boolean = password.length > 7

    private fun handleInputFields(){
        // TODO
    }

    // Firebase things
    private fun handleAuthentication() {
        auth.signInWithEmailAndPassword(
            binding.emailInput.text.toString(),
            binding.passwordInput.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful) {
                goToChats()
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
            } else {
                binding.loading.visibility = View.GONE
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun resetPassword(){
        auth.sendPasswordResetEmail(
            binding.emailInput.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful) {
                Toast.makeText(this, "Recovery password email sent", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
    // End of Firebase things

    private fun goToChats() {
        startActivity<ChatActivity>()
        finish()
    }

}