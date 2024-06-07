package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.SearchRepository
import android.employer_market.network.models.ResumeFilter
import android.employer_market.network.models.ResumeModel
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
        val searchInput: String = "",
        val currentFilter: ResumeFilter = ResumeFilter.None,
        val showFilterDialog: Boolean = false,
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
                        Log.d("MyTag", _uiState.value.resumes.size.toString())
                    },
                    onFailureAction = {}
                )
            }

            is SearchEvent.ChangeLiked -> {
//                TODO()
            }

            is SearchEvent.Invite -> {
//                TODO()
            }

            is SearchEvent.SetFrom -> {
//                TODO()
            }

            is SearchEvent.SetSearchInput -> {
//                TODO()
            }

            is SearchEvent.SetTo -> {
//                TODO()
            }

            is SearchEvent.SetResumesFilter -> {
//                TODO()
            }

            is SearchEvent.ShowFilterDialog -> {
//                TODO()
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