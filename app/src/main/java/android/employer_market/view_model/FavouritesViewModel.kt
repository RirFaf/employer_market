package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.FavouritesRepository
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.employer_market.view_model.event.FavouritesEvent
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

sealed interface FavouritesUIState {
    data class Success(
        val resumes: List<ResumeModel> = listOf(),
        val showVacancies: Boolean = false,
        val myVacancies: List<VacancyModel> = listOf(),
        val chosenStudentId: String = "",
    ) : SearchUIState

    data object Error : FavouritesUIState
    data object Loading : FavouritesUIState
}

class FavouritesViewModel(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val _uiState = MutableStateFlow(FavouritesUIState.Success())
    val uiState: StateFlow<FavouritesUIState.Success> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "FavouritesViewModel is cleared")
    }
    fun onEvent(event: FavouritesEvent){
        when(event){
            is FavouritesEvent.ChangeLiked -> {
                favouritesRepository.changeLiked(
                    resumeId = event.resumeId,
                    onFailureAction = {}
                )
            }
            is FavouritesEvent.GetLikedResumes -> {
                favouritesRepository.getLikedResumes(
                    onSuccessAction = {resumes->
                        _uiState.update {
                            it.copy(
                                resumes = resumes
                            )
                        }
                    },
                    onFailureAction = {}
                )
            }
            is FavouritesEvent.Invite -> {
                favouritesRepository.invite(
                    vacancyId = event.vacancyId,
                    studentId = event.studentId,
                    onFailureAction = {}
                )
            }

            is FavouritesEvent.GetMyVacancies -> {
                favouritesRepository.getMyVacancies(
                    onSuccessAction = { vacancies ->
                        _uiState.update {
                            it.copy(
                                myVacancies = vacancies
                            )
                        }
                    },
                    onFailureAction = {}
                )
            }
            is FavouritesEvent.SetChosenStudentId ->  {
                _uiState.update {
                    it.copy(
                        chosenStudentId = event.input
                    )
                }
            }
            is FavouritesEvent.ShowVacancies -> {
                _uiState.update {
                    it.copy(
                        showVacancies = !_uiState.value.showVacancies
                    )
                }
            }
        }
    }

    init {
        Log.i(
            tag, "FavouritesViewModel initialized"
        )
        onEvent(FavouritesEvent.GetLikedResumes)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
                val favouritesRepository = application.container.favouritesRepository
                FavouritesViewModel(
                    favouritesRepository = favouritesRepository
                )
            }
        }
    }
}