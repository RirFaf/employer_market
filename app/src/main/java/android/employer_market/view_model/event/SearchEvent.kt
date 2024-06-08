package android.employer_market.view_model.event

import android.employer_market.network.models.ResumeFilter

sealed interface SearchEvent {
    data object GetResumes : SearchEvent
    data class SetResumesFilter(val filter: ResumeFilter) : SearchEvent
    data class SetSearchInput(val input: String) : SearchEvent
    data class SetFrom(val input: String) : SearchEvent
    data class SetTo(val input: String) : SearchEvent
    data object ShowFilterDialog : SearchEvent
    data class ChangeLiked(val resumeId: String) : SearchEvent
    data class Invite(val vacancyId: String, val studentId: String) : SearchEvent
    data object GetMyVacancies : SearchEvent
    data class SetChosenStudentId(val input: String) : SearchEvent
}