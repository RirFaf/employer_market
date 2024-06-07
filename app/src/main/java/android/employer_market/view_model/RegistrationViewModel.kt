package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.RegistrationRepository
import android.employer_market.data.repository.SMFirebase
import android.employer_market.view_model.event.RegistrationEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface RegUIState {
    data class Success(
        val companyName: String = "",
        val city: String = "",
        val email: String = "",
        val password: String = "",
        val password1: String = "",
    ) : RegUIState

    object Error : RegUIState
    object Loading : RegUIState
}

class RegViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val db = SMFirebase

    private val _uiState = MutableStateFlow(RegUIState.Success())//переделать под обработку REST

    val uiState: StateFlow<RegUIState.Success> = _uiState.asStateFlow()

    init {
        Log.i(tag, "RegViewModel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "RegViewModel is cleared")
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.AddUser -> {
                if (_uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank() && (_uiState.value.password == _uiState.value.password1)) {
                    registrationRepository.register(
                        login = _uiState.value.email,
                        password = _uiState.value.password,
                        city = _uiState.value.city,
                        companyName = _uiState.value.companyName,
                        onSuccessAction = {
                            event.onSuccessAction()
                        },
                        onFailureAction = {
                            event.onFailureAction()
                        }
                    )
                }
                if (uiState.value.email.isBlank()) {
                    event.onEmptyLoginAction()
                    Log.d("FirebaseTag", "Login empty")

                }
                if (uiState.value.password.isBlank() || (_uiState.value.password != _uiState.value.password1)) {
                    event.onEmptyPasswordAction()
                    Log.d("FirebaseTag", "password empty")
                }
            }

            is RegistrationEvent.SetCity -> {
                _uiState.update {
                    it.copy(
                        city = event.input
                    )
                }
            }

            is RegistrationEvent.SetCompanyName -> {
                _uiState.update {
                    it.copy(
                        companyName = event.input
                    )
                }
            }

            is RegistrationEvent.SetEmail -> {
                _uiState.update {
                    it.copy(
                        email = event.input,
                    )
                }
            }


            is RegistrationEvent.SetPassword -> {
                _uiState.update {
                    it.copy(
                        password = event.input,
                    )
                }
            }

            is RegistrationEvent.SetPassword1 -> {
                _uiState.update {
                    it.copy(
                        password1 = event.input,
                    )
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
                val registrationRepository = application.container.registrationRepository
                RegViewModel(registrationRepository = registrationRepository)
            }
        }
    }
}