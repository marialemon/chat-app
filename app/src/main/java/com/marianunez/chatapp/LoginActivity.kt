package com.marianunez.chatapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.marianunez.chatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // 1. declare Firebase Authentication
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        initUi()
        handleLogIn()
    }

    private fun initUi() {
        binding.logInButton.setOnClickListener {
            when {
                !isEmailValid(binding.emailInput.text.toString()) -> binding.emailInput.setBackgroundColor(
                    Color.RED
                )
                !isPasswordValid(binding.passwordInput.text.toString()) -> binding.passwordInput.setBackgroundColor(
                    Color.RED
                )
                else -> {
                    binding.emailInput.setBackgroundColor(Color.TRANSPARENT)
                    binding.passwordInput.setBackgroundColor(Color.TRANSPARENT)
                    binding.loading.visibility = View.VISIBLE


                    auth.signInWithEmailAndPassword(binding.emailInput.text.toString(), binding.passwordInput.text.toString()).addOnCompleteListener { task ->
                        if (task.isComplete && task.isSuccessful) {
                            goToChats()
                            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                        } else {
                            binding.loading.visibility = View.GONE
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.signUpButton.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun handleLogIn() {
        if (auth.currentUser != null) {
            goToChats()
        }
    }

    private fun isEmailValid(email: String): Boolean = email.contains("@") && email.isNotBlank()
    private fun isPasswordValid(password: String): Boolean = password.length > 7

    private fun goToChats() {
        Intent(this, ChatActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}