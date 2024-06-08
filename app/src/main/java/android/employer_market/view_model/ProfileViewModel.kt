package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.ProfileRepository
import android.employer_market.network.SessionManager
import android.employer_market.network.models.CompanyModel
import android.employer_market.network.models.StudentModel
import android.employer_market.network.models.User
import android.employer_market.network.models.UserAuthData
import android.employer_market.view_model.event.ProfileEvent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface ProfileUIState {
    data class Success(
        val company: CompanyModel = CompanyModel(
            id = Firebase.auth.currentUser!!.uid,
            userAuthData = UserAuthData("", Firebase.auth.currentUser!!.uid),
            name = "",
            city = "",
            age = "",
            profArea = "",
            about = ""
        )
    ) : ProfileUIState

    data object Error : ProfileUIState
    data object Loading : ProfileUIState
}

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val tag = "VMTAG"

    private val _uiState = MutableStateFlow(ProfileUIState.Success())
    val uiState: StateFlow<ProfileUIState.Success> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "ProfileViewModel is cleared")
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UpdateCompanyInfo -> {
                profileRepository.updateCurrentUserInfo(
                    user = _uiState.value.company,
                    onFailureAction = {}
                )
            }
            is ProfileEvent.SetAbout -> {
                _uiState.update {
                    it.copy(
                        company = _uiState.value.company.copy(
                            about = event.input
                        )
                    )
                }
            }

            is ProfileEvent.SetAge -> {
                _uiState.update {
                    it.copy(
                        company = _uiState.value.company.copy(
                            age = event.input
                        )
                    )
                }
            }

            is ProfileEvent.SetCity -> {
                _uiState.update {
                    it.copy(
                        company = _uiState.value.company.copy(
                            city = event.input
                        )
                    )
                }
            }

            is ProfileEvent.SetName -> {
                _uiState.update {
                    it.copy(
                        company = _uiState.value.company.copy(
                            name = event.input
                        )
                    )
                }
            }

            is ProfileEvent.SetProfArea -> {
                _uiState.update {
                    it.copy(
                        company = _uiState.value.company.copy(
                            profArea = event.input
                        )
                    )
                }
            }
        }
    }

    init {
        Log.i(tag, "ProfileViewModel initialized")
        profileRepository.getUserInfo(
            onSuccessAction = { user ->
                _uiState.update {
                    it.copy(
                        company = user
                    )
                }
            },
            onFailureAction = {}
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DefaultApplication)
                val profileRepository = application.container.profileRepository
                ProfileViewModel(
                    profileRepository = profileRepository
                )
            }
        }
    }
}