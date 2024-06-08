package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.CompanyModel
import android.employer_market.network.models.UserAuthData
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

interface ProfileRepository {
    fun getUserInfo(
        onSuccessAction: (CompanyModel) -> Unit,
        onFailureAction: () -> Unit
    )

    fun updateCurrentUserInfo(
        user: CompanyModel,
        onFailureAction: () -> Unit
    )
}

class FirebaseProfileRepository() : ProfileRepository {
    override fun getUserInfo(
        onSuccessAction: (CompanyModel) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val usersDocRef = Firebase.firestore.collection("company")
            .whereEqualTo("id", Firebase.auth.currentUser!!.uid)
        usersDocRef
            .get()
            .addOnSuccessListener {
                onSuccessAction(
                    CompanyModel(
                        id = Firebase.auth.currentUser!!.uid,
                        userAuthData = UserAuthData(
                            Firebase.auth.currentUser!!.email!!,
                            Firebase.auth.currentUser!!.uid
                        ),
                        name = it.last().data["name"].toString(),
                        city = it.last().data["city"].toString(),
                        age = it.last().data["age"].toString(),
                        profArea = it.last().data["profArea"].toString(),
                        about = it.last().data["about"].toString(),
                    )
                )
            }
    }

    override fun updateCurrentUserInfo(user: CompanyModel, onFailureAction: () -> Unit) {
        Firebase.firestore.collection("company")
            .whereEqualTo("id", Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                val docId = it.last().id
                Firebase.firestore.collection("company")
                    .document(docId)
                    .update(
                        mapOf(
                            "name" to user.name,
                            "city" to user.city,
                            "age" to user.age,
                            "profArea" to user.profArea,
                            "about" to user.about,
                        )
                    )
            }
            .addOnFailureListener {
                Log.e(TAG.FIREBASE, it.toString())
            }
    }
}