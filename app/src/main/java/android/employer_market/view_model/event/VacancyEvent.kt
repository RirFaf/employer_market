package android.employer_market.view_model.event

import android.employer_market.network.models.VacancyModel

sealed interface VacancyEvent {
    data object GetVacancies : VacancyEvent
    data object CreateEmptyVacancy : VacancyEvent
    data class SetVacancy(val input: VacancyModel) : VacancyEvent
    data class SetEdArea(val input: String) : VacancyEvent
    data class SetFormOfEmployment(val input: String) : VacancyEvent
    data class SetPosition(val input: String) : VacancyEvent
    data class SetRequirements(val input: String) : VacancyEvent
    data class SetSalary(val input: String) : VacancyEvent
}