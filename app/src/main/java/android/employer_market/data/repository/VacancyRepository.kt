package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

interface VacancyRepository {
    fun createEmptyVacancy(onFailureAction: () -> Unit)
    fun updateVacancy(
        vacancy: VacancyModel,
        vacancyId: String,
        onFailureAction: () -> Unit
    )

    fun getVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    )
}

class FirebaseVacancyRepository() : VacancyRepository {
    override fun createEmptyVacancy(onFailureAction: () -> Unit) {
        Firebase.firestore.collection("vacancy")
            .add(
                mapOf(
                    "companyId" to Firebase.auth.currentUser!!.uid,
                    "companyName" to "",
                    "edArea" to "",
                    "formOfEmployment" to "",
                    "location" to "",
                    "position" to "",
                    "requirements" to "",
                    "salary" to ""
                )
            )
    }

    override fun updateVacancy(
        vacancy: VacancyModel,
        vacancyId: String,
        onFailureAction: () -> Unit
    ) {
        val vacancyRef = Firebase.firestore.collection("vacancy")
        vacancyRef
            .document(vacancyId)
            .update(
                mapOf(
                    "edArea" to vacancy.edArea,
                    "formOfEmployment" to vacancy.formOfEmployment,
                    "location" to vacancy.location,
                    "position" to vacancy.position,
                    "requirements" to vacancy.requirements,
                    "salary" to vacancy.salary
                )
            )
            .addOnFailureListener {
                Log.e(TAG.FIREBASE, it.toString())
            }
    }

    override fun getVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.getMyVacancies(onSuccessAction, onFailureAction)
    }
}