package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.ResponsesRepository
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.employer_market.view_model.event.ResponsesEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface ResponsesUIState {
    data class Success(
        val responses: List<Pair<ResumeModel, VacancyModel>> = listOf(),
    ) : ResponsesUIState

    data object Error : ResponsesUIState
    data object Loading : ResponsesUIState
}

class ResponsesViewModel(
    private val responsesRepository: ResponsesRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val _uiState =
        MutableStateFlow(ResponsesUIState.Success())
    val uiState: StateFlow<ResponsesUIState.Success> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "ResponsesViewModel is cleared")
    }

    fun onEvent(event: ResponsesEvent) {
        when (event) {
            is ResponsesEvent.AddChat -> {
                responsesRepository.addChat(
                    vacancyId = event.vacancyId,
                    studentId = event.studentId,
                    companyId = event.companyId
                )
            }

            is ResponsesEvent.UpdateResponseStatus -> {
                responsesRepository.changeResponseStatus(
                    studentId = event.studentId,
                    vacancyId = event.vacancyId,
                    status = event.status,
                    onSuccessAction = {
                        responsesRepository.getResponses(
                            onSuccessAction = { responses ->
                                _uiState.update {
                                    it.copy(
                                        responses = responses
                                    )
                                }
                            },
                            onFailureAction = {}
                        )
                    },
                    onFailureAction = {}
                )
            }
        }
    }

    init {
        Log.i(
            tag, "ResponsesViewModel initialized"
        )
        responsesRepository.getResponses(
            onSuccessAction = { responses ->
                _uiState.update {
                    it.copy(
                        responses = responses
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
                val responsesRepository = application.container.responsesRepository
                ResponsesViewModel(
                    responsesRepository = responsesRepository
                )
            }
        }
    }
}