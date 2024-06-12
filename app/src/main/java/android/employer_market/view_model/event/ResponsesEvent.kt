package android.employer_market.view_model.event

import android.employer_market.network.models.VacancyModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

sealed interface ResponsesEvent {
    data class AddChat(
        val vacancyId: String,
        val companyId: String = Firebase.auth.currentUser!!.uid,
        val studentId: String
    ) : ResponsesEvent

    data class UpdateResponseStatus(
        val status: String,
        val vacancyId: String,
        val studentId: String
    ) : ResponsesEvent
}