package android.employer_market.data.repository

import android.employer_market.network.AuthApiService
import android.employer_market.network.models.requests.AuthRequest
import android.employer_market.network.models.responses.AuthResponse
import retrofit2.Call

interface LoginRepository {
    suspend fun login(authRequest: AuthRequest): Call<AuthResponse>
}

class NetworkLoginRepository(
    private val authApiService: AuthApiService
) : LoginRepository {
    override suspend fun login(authRequest: AuthRequest): Call<AuthResponse> =
        authApiService.login(authRequest)
}