package android.employer_market.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    /***Authentication***/
    @Serializable
    data object LogRegScreen : Screen()
    @Serializable
    data object RegistrationScreen : Screen()
    @Serializable
    data object CompanyInfoScreen : Screen()
    @Serializable
    data object EmailAndPasswordScreen : Screen()
    @Serializable
    data object LoginScreen : Screen()
    /***Main app***/
    @Serializable
    data object SearchScreen : Screen()
    @Serializable
    data class SelectedResumeScreen(
        var id: String = "Не указано",
        var studentId: String = "Не указано",
        var salary: String = "Не указано",
        var keySkills: String = "Не указано",
        var secondName: String = "Не указано",
        var firstName: String = "Не указано",
        var patronymicName: String = "Не указано",
        var birthDate: String = "Не указано",
        var university: String = "Не указано",
        var institute: String = "Не указано",
        var course: String = "Не указано",
        var aboutMe: String = "Не указано",
        var gender: String = "Не указано",
        var city: String = "Не указано",
        var direction: String = "Не указано",
        var liked: Boolean = false,
    ) : Screen()
    @Serializable
    data object FavouritesScreen : Screen()
    @Serializable
    data object ResponsesListScreen : Screen()
    /***Messenger***/
    @Serializable
    data object ChatListScreen : Screen()
    @Serializable
    data object MessengerScreen : Screen()
    @Serializable
    data object Messenger:Screen()
    /***Profile***/
    @Serializable
    data object Profile:Screen()
    @Serializable
    data object ProfileScreen : Screen()
    @Serializable
    data object ProfileRedactorScreen : Screen()
    /***Resume***/
    @Serializable
    data object VacancyScreen : Screen()
    @Serializable
    data object VacancyRedactorScreen : Screen()
    @Serializable
    data object VacanciesListScreen : Screen()
    @Serializable
    data object Vacancy:Screen()
}