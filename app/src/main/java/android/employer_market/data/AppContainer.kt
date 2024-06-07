package android.employer_market.data

import android.employer_market.data.repository.LoginRepository
import android.employer_market.data.repository.NetworkLoginRepository
import android.employer_market.data.repository.NetworkRegistrationRepository
import android.employer_market.data.repository.RegistrationRepository
import android.employer_market.network.AuthApiClient

interface AppContainer {
    val loginRepository: LoginRepository
    val registrationRepository: RegistrationRepository
}

class DefaultAppContainer : AppContainer {
    private val authApiClient = AuthApiClient()

    private val retrofitApiService = authApiClient.getApiService()

    override val loginRepository: LoginRepository by lazy {
        NetworkLoginRepository(retrofitApiService)
    }
    override val registrationRepository: RegistrationRepository by lazy {
        NetworkRegistrationRepository(retrofitApiService)
    }
}