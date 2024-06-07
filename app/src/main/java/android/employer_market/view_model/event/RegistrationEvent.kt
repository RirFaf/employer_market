package android.employer_market.view_model.event

sealed interface RegistrationEvent {
    data class SetCompanyName   ( val input: String) : RegistrationEvent
    data class SetCity(val input: String) : RegistrationEvent
    data class SetEmail(val input: String) : RegistrationEvent
    data class SetPassword(val input: String) : RegistrationEvent
    data class SetPassword1(val input: String) : RegistrationEvent
    data class AddUser(
        val onSuccessAction: () -> Unit,
        val onFailureAction: () -> Unit,
        val onEmptyPasswordAction: () -> Unit,
        val onEmptyLoginAction: () -> Unit,
    ) : RegistrationEvent
}