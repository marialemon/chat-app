package com.marianunez.chatapp.commons

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

// función para inflar vistas
fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater
        .from(context)
        .inflate(layoutRes, this, false)

inline fun <reified T: Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java))


inline fun <reified T> Context.startActivityWithExtras(apply: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    apply(intent)
    startActivity(intent)
}
