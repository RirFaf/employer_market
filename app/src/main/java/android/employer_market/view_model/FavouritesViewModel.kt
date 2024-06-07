package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
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

sealed interface FavouritesUIState {
    data class Success(
        val favourites: List<ResumeModel> = listOf(),
    ) : SearchUIState

    data object Error : FavouritesUIState
    data object Loading : FavouritesUIState
}

class FavouritesViewModel(
//    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private val tag = "VMTAG"
    private val _uiState = MutableStateFlow(FavouritesUIState.Success())
    val uiState: StateFlow<FavouritesUIState.Success> = _uiState.asStateFlow()

    init {
        Log.i(
            tag, "FavouritesViewModel initialized"
        )
        onEvent(FavouritesEvent.GetVacancies)
    }
    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "FavouritesViewModel is cleared")
    }
    fun onEvent(event: FavouritesEvent){
        when(event){
            FavouritesEvent.GetVacancies -> {}//TODO
            FavouritesEvent.RespondToVacancy -> {}//TODO
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
//                val searchRepository = application.container.favouritesRepository
//                val sessionManager = application.sessionManager
                FavouritesViewModel(
//                    favouritesRepository = favouritesRepository
                )
            }
        }
    }
}