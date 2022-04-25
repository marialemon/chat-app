package com.marianunez.chatapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

// https://firebaseopensource.com/projects/firebase/firebaseui-android/firestore/readme/
// this class will contain basic functionality for the others adapters
abstract class FirestoreAdapter<ViewHolder : RecyclerView.ViewHolder?>(private var query: Query) :
    RecyclerView.Adapter<ViewHolder>(), EventListener<QuerySnapshot> {

    private var registration: ListenerRegistration? = null
    private val snapshots = ArrayList<DocumentSnapshot>()

    companion object {
        const val TAG = "Firestore Adapter"
    }

    fun startListening() {
        if (registration == null) {
            registration = query.addSnapshotListener(this)
        }
    }

    fun stopListening() {
        if (registration != null) {
            registration?.remove()
            registration = null
        }
        snapshots.clear()
        notifyDataSetChanged()
    }

}