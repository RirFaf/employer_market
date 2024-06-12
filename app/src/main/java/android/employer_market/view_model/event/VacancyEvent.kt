package android.employer_market.view_model.event

import android.employer_market.network.models.VacancyModel

sealed interface VacancyEvent {
    data object GetVacancies : VacancyEvent
    data object CreateVacancy : VacancyEvent
    data object UpdateCurrentVacancy : VacancyEvent
    data class DeleteVacancy(val vacancy: VacancyModel) : VacancyEvent
    data class SetIsCurrentVacancyNew(val new: Boolean) : VacancyEvent
    data class SetVacancy(val input: VacancyModel) : VacancyEvent
    data class SetEdArea(val input: String) : VacancyEvent
    data class SetFormOfEmployment(val input: String) : VacancyEvent
    data class SetPosition(val input: String) : VacancyEvent
    data class SetRequirements(val input: String) : VacancyEvent
    data class SetSalary(val input: String) : VacancyEvent
}