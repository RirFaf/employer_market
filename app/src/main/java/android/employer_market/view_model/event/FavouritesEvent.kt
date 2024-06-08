package android.employer_market.view_model.event

sealed interface FavouritesEvent {
    data class Invite(val vacancyId: String, val studentId: String) : FavouritesEvent
    data class ChangeLiked(val resumeId: String) : FavouritesEvent
    data object GetLikedResumes : FavouritesEvent
    data object ShowVacancies : FavouritesEvent
    data class SetChosenStudentId(val input: String) : FavouritesEvent
    data object GetMyVacancies : FavouritesEvent
}