package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.VacancyRepository
import android.employer_market.network.models.VacancyModel
import android.employer_market.view_model.event.VacancyEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface VacancyUIState {
    data class Success(
        val vacancies: List<VacancyModel> = listOf(),
        val currentVacancy: VacancyModel = VacancyModel(),
        val isCurrentVacancyNew: Boolean = false
    ) : VacancyUIState

    data object Error : VacancyUIState
    data object Loading : VacancyUIState
}

class VacancyViewModel(
    private val vacancyRepository: VacancyRepository
) : ViewModel() {
    private val tag = "VMTAG"

    private val _uiState = MutableStateFlow(VacancyUIState.Success())
    val uiState: StateFlow<VacancyUIState.Success> = _uiState.asStateFlow()


    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "VacancyViewModel is cleared")
    }

    fun onEvent(event: VacancyEvent) {
        when (event) {
            is VacancyEvent.SetEdArea -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = _uiState.value.currentVacancy.copy(
                            edArea = event.input
                        )
                    )
                }
            }

            is VacancyEvent.SetFormOfEmployment -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = _uiState.value.currentVacancy.copy(
                            formOfEmployment = event.input
                        )
                    )
                }
            }

            is VacancyEvent.SetPosition -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = _uiState.value.currentVacancy.copy(
                            position = event.input
                        )
                    )
                }
            }

            is VacancyEvent.SetRequirements -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = _uiState.value.currentVacancy.copy(
                            requirements = event.input
                        )
                    )
                }
            }

            is VacancyEvent.SetSalary -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = _uiState.value.currentVacancy.copy(
                            salary = event.input.ifBlank { "0" }.toInt()
                        )
                    )
                }
            }

            is VacancyEvent.SetVacancy -> {
                _uiState.update {
                    it.copy(
                        currentVacancy = event.input
                    )
                }
            }

            is VacancyEvent.SetIsCurrentVacancyNew -> {
                _uiState.update {
                    it.copy(
                        isCurrentVacancyNew = event.new
                    )
                }
            }

            is VacancyEvent.CreateVacancy -> {
                vacancyRepository.createVacancy(
                    vacancy = _uiState.value.currentVacancy,
                    onFailureAction = {}
                )
            }

            VacancyEvent.UpdateCurrentVacancy -> {
                vacancyRepository.updateVacancy(
                    vacancy = _uiState.value.currentVacancy,
                    onFailureAction = {}
                )
            }

            is VacancyEvent.GetVacancies -> {
                vacancyRepository.getVacancies(
                    onSuccessAction = { vacancies ->
                        _uiState.update {
                            it.copy(
                                vacancies = vacancies
                            )
                        }
                    },
                    onFailureAction = {}
                )
            }

            is VacancyEvent.DeleteVacancy -> {
                vacancyRepository.deleteVacancy(vacancy = event.vacancy, {})
            }
        }
    }

    init {
        Log.i(tag, "VacancyViewModel initialized")
        onEvent(VacancyEvent.GetVacancies)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DefaultApplication)
                val vacancyRepository = application.container.vacancyRepository
                VacancyViewModel(
                    vacancyRepository = vacancyRepository
                )
            }
        }
    }
}