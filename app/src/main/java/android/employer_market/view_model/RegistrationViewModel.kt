package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.RegistrationRepository
import android.employer_market.network.SMFirebase
import android.employer_market.network.models.CompanyModel
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
        val companyname: String = "",
        val city: String = "",
        val course: String = "",
        val email: String = "",
        val password: String = "",
        val isPasswordBlank: Boolean = true,
        val isLoginBlank: Boolean = true,
    ) : RegUIState

    object Error : RegUIState
    object Loading : RegUIState
}

class RegViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val db = SMFirebase()

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
                if (!_uiState.value.isLoginBlank && !_uiState.value.isPasswordBlank) {
                    db.addUser(
                        CompanyModel(
                            companyName = _uiState.value.companyname,
                            city = _uiState.value.city,
                            email = _uiState.value.email,
                            password = _uiState.value.password,
                        ),
                        onSuccessAction = {
                            event.onSuccessAction()
                        },
                        onFailureAction = {
                            event.onFailureAction()
                        }
                    )
                } else if (!uiState.value.isLoginBlank) {
                    event.onEmptyLoginAction()
                } else {
                    event.onEmptyPasswordAction()
                }
            }

            is RegistrationEvent.SetCity -> {
                _uiState.update {
                    it.copy(
                        city = event.input
                    )
                }
            }


            is RegistrationEvent.SetEmail -> {
                _uiState.update {
                    it.copy(
                        email = event.input,
                        isLoginBlank = event.input.isBlank()
                    )
                }
            }


            is RegistrationEvent.SetPassword -> {
                _uiState.update {
                    it.copy(
                        password = event.input,
                        isPasswordBlank = event.input.isBlank()
                    )
                }
            }

            is RegistrationEvent.SetCompanyName -> {
                _uiState.update {
                    it.copy(
                        companyname = event.input
                    )
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
                val registrationRepository = application.container.registrationRepository // TODO:
                val sessionManager = application.sessionManager
                RegViewModel(registrationRepository = registrationRepository)
            }
        }
    }
}