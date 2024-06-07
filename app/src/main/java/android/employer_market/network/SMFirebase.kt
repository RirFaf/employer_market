package android.employer_market.network

import android.employer_market.network.models.CompanyModel
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object SMFirebase {
    private val tag = "FirebaseTag"
    private val auth = Firebase.auth

    fun addUser(
        user: CompanyModel,
        onSuccessAction: () -> Unit,
        onFailureAction: () -> Unit,
    ) {
        val rootRef = Firebase.database.getReference("Employer")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                rootRef.push().setValue(user)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(tag, databaseError.message) //Don't ignore errors!
            }
        }

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                rootRef.addListenerForSingleValueEvent(eventListener)
                onSuccessAction()
            }
            .addOnFailureListener{
                onFailureAction()
            }
    }


    /* TODO: Исправить валидацию (поля меняют цвет в любом случае) */
    fun loginUser(
        onSuccessAction: () -> Unit,
        login: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                onSuccessAction()
            }
            .addOnFailureListener {
                Log.e(tag, it.toString())
            }
    }

    fun logoutUser(
        onLogoutAction: () -> Unit
    ) {
        auth.signOut()
        onLogoutAction()
    }

    fun authenticateUser(): Boolean {
        return Firebase.auth.currentUser != null
    }
}






