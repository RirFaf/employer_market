package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.SearchRepository
import android.employer_market.network.models.ResumeFilter
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.employer_market.view_model.event.FavouritesEvent
import android.employer_market.view_model.event.SearchEvent
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

sealed interface SearchUIState {
    data class Success(
        val resumes: List<ResumeModel> = listOf(),
        val myVacancies: List<VacancyModel> = listOf(),
        val chosenStudentId: String = "",
        val searchInput: String = "",
        val currentFilter: ResumeFilter = ResumeFilter.None,
        val showFilterDialog: Boolean = false,
        val showVacancies: Boolean = false,
        val from: Int = 0,
        val to: Int = 0
    ) : SearchUIState

    object Error : SearchUIState
    object Loading : SearchUIState

}

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val _uiState = MutableStateFlow(SearchUIState.Success())
    val uiState: StateFlow<SearchUIState.Success> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "SearchViewModel is cleared")
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.GetResumes -> {
                searchRepository.getResumes(
                    search = _uiState.value.searchInput,
                    filter = _uiState.value.currentFilter,
                    onSuccessAction = { resumes ->
                        _uiState.update {
                            it.copy(
                                resumes = resumes
                            )
                        }
                    },
                    onFailureAction = {}
                )
            }

            is SearchEvent.ChangeLiked -> {
                searchRepository.changeLiked(resumeId = event.resumeId, {})
            }

            is SearchEvent.Invite -> {
                searchRepository.invite(
                    vacancyId = event.vacancyId,
                    studentId = event.studentId,
                    onFailureAction = {}
                )
            }

            is SearchEvent.SetResumesFilter -> {
                _uiState.update {
                    it.copy(
                        currentFilter = event.filter
                    )
                }
            }
            is SearchEvent.SetFrom -> {
                _uiState.update {
                    it.copy(
                        from = if (event.input != "") {
                            event.input.toInt()
                        } else {
                            0
                        }
                    )
                }
            }

            is SearchEvent.SetTo -> {
                _uiState.update {
                    it.copy(
                        to = if (event.input != "") {
                            event.input.toInt()
                        } else {
                            0
                        }
                    )
                }
            }

            is SearchEvent.SetSearchInput -> {
                _uiState.update {
                    it.copy(
                        searchInput = event.input
                    )
                }
            }


            is SearchEvent.ShowFilterDialog -> {
                _uiState.update {
                    it.copy(
                        showFilterDialog = !_uiState.value.showFilterDialog
                    )
                }
            }

            is SearchEvent.ShowVacancies -> {
                _uiState.update {
                    it.copy(
                        showVacancies = !_uiState.value.showVacancies
                    )
                }
            }

            SearchEvent.GetMyVacancies -> {
                searchRepository.getMyVacancies(
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

            is SearchEvent.SetChosenStudentId -> {
                _uiState.update {
                    it.copy(
                        chosenStudentId = event.input
                    )
                }
            }
        }
    }

    init {
        Log.i(
            tag, "SearchViewModel initialized"
        )
        onEvent(SearchEvent.GetResumes)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
                val searchRepository = application.container.searchRepository
                SearchViewModel(
                    searchRepository = searchRepository
                )
            }
        }
    }
}