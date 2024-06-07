package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.CompanyModel
import android.employer_market.network.models.UserAuthData
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface RegistrationRepository {
    fun register(
        login: String,
        password: String,
        companyName: String,
        city: String,
        onSuccessAction: () -> Unit,
        onFailureAction: () -> Unit,
    )
}

class FirebaseRegistrationRepository() : RegistrationRepository {
    override fun register(
        login: String,
        password: String,
        companyName: String,
        city: String,
        onSuccessAction: () -> Unit,
        onFailureAction: () -> Unit,
    ) {
        val usersDocRef = Firebase.firestore.collection("company")
        Firebase.auth.createUserWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                Firebase.auth.currentUser?.let { currentUser ->
                    usersDocRef.add(
                        CompanyModel(
                            userAuthData = UserAuthData(login, currentUser.uid),
                            name = companyName,
                            city = city,
                            id = currentUser.uid
                        )
                    )
                }
                onSuccessAction()
            }
            .addOnFailureListener {
                Log.e(TAG.FIREBASE, it.toString())
                onFailureAction()
            }
    }
}