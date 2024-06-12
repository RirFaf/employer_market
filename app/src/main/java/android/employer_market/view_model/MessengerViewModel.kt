package android.employer_market.view_model

import android.employer_market.app.DefaultApplication
import android.employer_market.data.repository.MessengerRepository
import android.employer_market.network.models.ChatModel
import android.employer_market.network.models.MessageModel
import android.employer_market.view_model.event.MessengerEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface MessengerUIState {
    data class Success(
        val enteredText: String = "",
        val messages: List<MessageModel> = listOf(),
        val currentChatId: String = "",
        val chats: List<ChatModel> = listOf(),
        val currentUserid: String = Firebase.auth.currentUser!!.uid,
        val currentVacancyName: String = "",
        val currentStudentName: String = "",
    ) : MessengerUIState

    data object Error : MessengerUIState
    data object Loading : MessengerUIState
}

class MessengerViewModel(
    private val messengerRepository: MessengerRepository
) : ViewModel() {
    private val tag = "VMTAG"

    private val _uiState = MutableStateFlow(MessengerUIState.Success())
    val uiState: StateFlow<MessengerUIState.Success> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "MessengerViewModel is cleared")
    }

    fun onEvent(event: MessengerEvent) {
        when (event) {
            is MessengerEvent.SendMessage -> {
                if(_uiState.value.enteredText.isNotBlank()){
                    messengerRepository.sendMessage(
                        message = _uiState.value.enteredText,
                        currentChatId = _uiState.value.currentChatId
                    )
                }
            }

            is MessengerEvent.SetMessage -> {
                _uiState.update {
                    it.copy(
                        enteredText = event.input
                    )
                }
            }

            is MessengerEvent.GetMessages -> {
                _uiState.update {
                    it.copy(
                        currentChatId = event.chatId,
                        currentStudentName = event.studentName,
                        currentVacancyName = event.vacancyName,
                    )
                }
                messengerRepository.getMessages(
                    currentChatId = _uiState.value.currentChatId,
                    onDataChanged = { messages ->
                        _uiState.update {
                            it.copy(
                                messages = messages
                            )
                        }
                    }
                )
            }
        }
    }

    init {
        messengerRepository.getChats(
            onDataChanged = { chats ->
                _uiState.update {
                    it.copy(
                        chats = chats
                    )
                }
                if (_uiState.value.messages.isEmpty() && _uiState.value.chats.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            currentChatId = _uiState.value.chats.last().chatId,
                            currentStudentName = _uiState.value.chats.last().studentName,
                            currentVacancyName = _uiState.value.chats.last().vacancyName,
                        )
                    }
                    messengerRepository.getMessages(
                        currentChatId = _uiState.value.currentChatId,
                        onDataChanged = { messages ->
                            _uiState.update {
                                it.copy(
                                    messages = messages
                                )
                            }
                        }
                    )
                }
            },
            onFailureAction = {}
        )
        Log.i(
            tag, "MessengerViewModel initialized"
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DefaultApplication)
                val messengerRepository = application.container.messengerRepository
                MessengerViewModel(
                    messengerRepository = messengerRepository
                )
            }
        }
    }
}