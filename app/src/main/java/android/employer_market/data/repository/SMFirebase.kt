package android.employer_market.data.repository

import android.employer_market.data.constants.ResponseStatus
import android.employer_market.data.constants.TAG
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.firestore
import com.google.firebase.ktx.Firebase

object SMFirebase {
    private val auth = Firebase.auth

    fun getMyVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val vacancies = ArrayList<VacancyModel>()
        com.google.firebase.Firebase.firestore.collection("vacancy")
            .whereEqualTo("companyId", com.google.firebase.Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    vacancies.add(
                        VacancyModel(
                            id = doc.id,
                            edArea = doc.data["edArea"].toString(),
                            formOfEmployment = doc.data["formOfEmployment"].toString(),
                            location = doc.data["location"].toString(),
                            position = doc.data["position"].toString(),
                            requirements = doc.data["requirements"].toString(),
                            salary = doc.data["salary"].toString().ifBlank { "0" }.toInt(),
                            about = doc.data["about"].toString()
                        )
                    )
                }
                onSuccessAction(vacancies)
            }
            .addOnFailureListener {
                onFailureAction()
                Log.e(TAG.FIREBASE, it.toString())
            }
    }

    fun invite(
        vacancyId: String,
        studentId: String,
        onFailureAction: () -> Unit
    ) {
        val responsesRef =
            Firebase.database.getReference("responses").child(studentId)
        var alreadyAdded = false
        responsesRef
            .get()
            .addOnSuccessListener {
                for (data in it.children) {
                    if (data.child("vacancyId").value == vacancyId) {
                        alreadyAdded = true
                        break
                    }
                }
                if (!alreadyAdded) {
                    responsesRef
                        .push()
                        .setValue(
                            mapOf(
                                "vacancyId" to vacancyId,
                                "companyId" to Firebase.auth.currentUser!!.uid,
                                "status" to ResponseStatus.APPROVED
                            )
                        )
                }
            }
            .addOnFailureListener {
                onFailureAction()
                Log.e(TAG.FIREBASE, it.stackTraceToString())
            }
    }

    fun changeLiked(
        vacancyId: String,
        onFailureAction: () -> Unit
    ) {
        val likesRef =
            com.google.firebase.Firebase.database.getReference("companyLikes")
                .child(com.google.firebase.Firebase.auth.currentUser!!.uid)
        var alreadyAdded = false
        likesRef
            .get()
            .addOnSuccessListener {
                for (data in it.children) {
                    if (data.value == vacancyId) {
                        alreadyAdded = true
                        data.ref.removeValue()
                        break
                    }
                }
                if (!alreadyAdded) {
                    likesRef
                        .push()
                        .setValue(vacancyId)
                }
            }
            .addOnFailureListener {
                onFailureAction()
                Log.e(TAG.FIREBASE, it.stackTraceToString())
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






