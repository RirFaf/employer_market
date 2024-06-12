package android.employer_market.data.repository

interface RepositoryContainer {
    val loginRepository: LoginRepository
    val registrationRepository: RegistrationRepository
    val searchRepository: SearchRepository
    val favouritesRepository: FavouritesRepository
    val profileRepository: ProfileRepository
    val vacancyRepository: VacancyRepository
    val responsesRepository: ResponsesRepository
    val messengerRepository: MessengerRepository
}

class DefaultRepositoryContainer : RepositoryContainer {
    override val loginRepository: LoginRepository by lazy {
        FirebaseLoginRepository()
    }
    override val registrationRepository: RegistrationRepository by lazy {
        FirebaseRegistrationRepository()
    }
    override val searchRepository: SearchRepository by lazy {
        FirebaseSearchRepository()
    }
    override val favouritesRepository: FavouritesRepository by lazy {
        FirebaseFavouritesRepository()
    }
    override val profileRepository: ProfileRepository by lazy {
        FirebaseProfileRepository()
    }
    override val vacancyRepository: VacancyRepository by lazy {
        FirebaseVacancyRepository()
    }
    override val responsesRepository: ResponsesRepository by lazy {
        FirebaseResponsesRepository()
    }
    override val messengerRepository: MessengerRepository by lazy {
        FirebaseMessengerRepository()
    }
}