package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.CompanyModel
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

interface VacancyRepository {
    fun createVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    )

    fun deleteVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    )

    fun updateVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    )

    fun getVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    )
}

class FirebaseVacancyRepository() : VacancyRepository {
    override fun deleteVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    ) {
        Firebase.firestore.collection("vacancy")
            .document(vacancy.id)
            .delete()
    }
    override fun createVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    ) {
        Firebase.firestore.collection("company")
            .whereEqualTo("id", Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {companiesSnapshot->
                Log.d("MyTag", companiesSnapshot.last().data["name"].toString())
                Firebase.firestore.collection("vacancy")
                    .add(
                        mapOf(
                            "companyId" to Firebase.auth.currentUser!!.uid,
                            "companyName" to companiesSnapshot.last().data["name"].toString(),
                            "edArea" to vacancy.edArea,
                            "formOfEmployment" to vacancy.formOfEmployment,
                            "location" to vacancy.location,
                            "position" to vacancy.position,
                            "requirements" to vacancy.requirements,
                            "salary" to vacancy.salary
                        )
                    )
            }
    }

    override fun updateVacancy(
        vacancy: VacancyModel,
        onFailureAction: () -> Unit
    ) {
        val vacancyRef = Firebase.firestore.collection("vacancy")
        vacancyRef
            .document(vacancy.id)
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