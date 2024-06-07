package android.employer_market.data.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object SMFirebase {
    private val tag = "FirebaseTag"
    private val auth = Firebase.auth

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






