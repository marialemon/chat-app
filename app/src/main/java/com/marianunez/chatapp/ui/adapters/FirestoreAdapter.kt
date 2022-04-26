package com.marianunez.chatapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
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
        // tag to use in the logs
        const val TAG = "Firestore Adapter"
    }

    // we are going to use these methods as soon as we want the adapters to start and stop listening the changes
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

    private fun onDocumentAdded(change: DocumentChange) {
        snapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    private fun onDocumentMofified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) { // item changed but remained in the same position
            snapshots[change.oldIndex] = change.document
            notifyItemChanged(change.oldIndex)
        } else { // item changed and changed position
            snapshots.removeAt(change.oldIndex)
            snapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    private fun onDocumentRemoved(change: DocumentChange) {
        snapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

}