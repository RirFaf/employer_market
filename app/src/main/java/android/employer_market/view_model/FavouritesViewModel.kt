package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.network.models.VacanciesModel
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
        val favourites: VacanciesModel,
        val favouriteVacancy: VacancyModel
    ) : SearchUIState

    data object Error : FavouritesUIState
    data object Loading : FavouritesUIState
}

class FavouritesViewModel(
//    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private val vacancies = VacanciesModel(
        listOf(
            VacancyModel(
                id = 0,
                position = "IOS-Разработчик",
                salary = 80000,
                companyName = "Александр Кокошкин",
                edArea = "IT-технологии",
                formOfEmployment = "Полная",
                requirements = "Диплом о законченом высшем образовании",
                location = "Казань",
                about = "Хороший и ответственный сотрудник",
            ),
            VacancyModel(
                id = 1,
                position = "Android-Разработчик",
                salary = 60000,
                companyName = "Виктор Полунин",
                edArea = "IT-технологии",
                formOfEmployment = "Полная",
                requirements = "Диплом о незаконченном высшем образовании",
                location = "Казань",
                about = "Порядочный сотрудник",
            ),
            VacancyModel(
                id = 2,
                position = "Web-Дизайнер",
                salary = 40000,
                companyName = "Ольга Михеева",
                edArea = "Web-технологии",
                formOfEmployment = "Неполная",
                requirements = "Диплом о среднем образовании",
                location = "Казань",
                about = "Со своим неповторимым вкусом",
            ),
        )
    )
    private val tag = "VMTAG"
    private val _uiState = MutableStateFlow(FavouritesUIState.Success(vacancies, favouriteVacancy = vacancies.vacancies[0]))
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