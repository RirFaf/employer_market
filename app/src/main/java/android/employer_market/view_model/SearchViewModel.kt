package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.network.models.VacanciesModel
import android.employer_market.network.models.VacancyModel
import android.employer_market.view_model.event.SearchEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SearchUIState {
    data class Success(
        val vacancies: VacanciesModel,
        val vacancy: VacancyModel = VacancyModel(
            id = 0,
            position = "",
            salary = 0,
            companyName = "",
            edArea = "",
            formOfEmployment = "",
            requirements = "",
            location = "",
            about = "",
        )
    ) : SearchUIState

    object Error : SearchUIState
    object Loading : SearchUIState

}

class SearchViewModel(
//    private val searchRepository: SearchRepository
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
    private val _uiState = MutableStateFlow(SearchUIState.Success(vacancies))
    val uiState: StateFlow<SearchUIState.Success> = _uiState.asStateFlow()

    init {
        Log.i(
            tag, "SearchViewModel initialized"
        )
        onEvent(SearchEvent.GetVacancies)
    }
    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "SearchViewModel is cleared")
    }

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.GetVacancies -> {
                viewModelScope.launch {
//            searchRepository.getVacanciesList().asResult()
//            _uiState = SearchUIState.Loading
//            _uiState = try {
//                SearchUIState.Success(searchRepository.getVacanciesList())
//            } catch (e: IOException) {
//                SearchUIState.Error
//            } catch (e: HttpException) {
//                SearchUIState.Error
//            }
                }
            }

            is SearchEvent.GetVacanciesBySearch -> {}//TODO имплементировать поиск вакансий
            is SearchEvent.RespondToVacancy -> {}
            is SearchEvent.Respond -> {}
            is SearchEvent.SetFavourite -> {}
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DefaultApplication)
//                val searchRepository = application.container.searchRepository
//                val sessionManager = application.sessionManager
                SearchViewModel(
//                    searchRepository = searchRepository
                )
            }
        }
    }
}