package android.employer_market.data

import android.employer_market.data.repository.FirebaseLoginRepository
import android.employer_market.data.repository.LoginRepository
import android.employer_market.data.repository.FirebaseRegistrationRepository
import android.employer_market.data.repository.RegistrationRepository

interface RepositoryContainer {
    val loginRepository: LoginRepository
    val registrationRepository: RegistrationRepository
}

class DefaultRepositoryContainer : RepositoryContainer {
    override val loginRepository: LoginRepository by lazy {
        FirebaseLoginRepository()
    }
    override val registrationRepository: RegistrationRepository by lazy {
        FirebaseRegistrationRepository()
    }
}