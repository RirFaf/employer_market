package android.employer_market.view_model.event

import android.employer_market.network.models.VacancyModel

sealed interface ResponsesEvent {
    data object GetResponses : ResponsesEvent
    data class DeleteResponse(val input: VacancyModel) : ResponsesEvent
}