package android.employer_market.data.repository

interface RepositoryContainer {
    val loginRepository: LoginRepository
    val registrationRepository: RegistrationRepository
    val searchRepository: SearchRepository
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
}