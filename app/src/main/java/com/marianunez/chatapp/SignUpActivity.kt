package com.marianunez.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.marianunez.chatapp.commons.startActivity
import com.marianunez.chatapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signUpButton.setOnClickListener {
            handleSignUp()
        }
    }

    private fun handleSignUp() {
        when {
            !isUsernameValid(binding.userNameInput.text.toString()) -> binding.usernameLayout.error =
                "User name not valid"
            !isEmailValid(binding.emailInput.text.toString()) -> binding.emailLayout.error =
                "Email not valid"
            !isPasswordValid(binding.passwordInput.text.toString()) -> binding.passwordLayout.error =
                "Password not valid"
            !isPasswordMatch(
                binding.passwordInput.text.toString(),
                binding.passwordRepeatInput.text.toString()
            ) -> binding.passwordRepeatInputLayout.error =
                "Password doesn't match"
            else -> {
                binding.apply {
                    usernameLayout.isErrorEnabled = false
                    emailLayout.isErrorEnabled = false
                    passwordLayout.isErrorEnabled = false
                    passwordRepeatInputLayout.isErrorEnabled = false
                    loading.visibility = View.VISIBLE
                }

                auth.createUserWithEmailAndPassword(
                    binding.emailInput.text.toString().trim(),
                    binding.passwordInput.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isComplete && task.isSuccessful) {
                            val newUser = UserProfileChangeRequest.Builder()
                                .setDisplayName(binding.userNameInput.text.toString())
                                .build()
                            auth.currentUser?.updateProfile(newUser)

                            startActivity<ChatActivity>()
                            finish()
                        } else {
                            binding.loading.visibility = View.GONE
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    // Validations
    private fun isEmailValid(email: String): Boolean =
        email.contains("@") && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isUsernameValid(username: String): Boolean = username.isNotBlank()
    private fun isPasswordValid(password: String): Boolean = password.length > 7
    private fun isPasswordMatch(password: String, secondPassword: String): Boolean =
        password == secondPassword

    private fun goBackToLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}