package android.skills_market.ui.screens.messenger

import android.skills_market.R
import android.skills_market.network.models.MessageModel
import android.skills_market.network.models.User
import android.skills_market.ui.theme.Inter
import android.skills_market.ui.theme.md_theme_dark_inverseOnSurface
import android.skills_market.view_model.MessengerUIState
import android.skills_market.view_model.event.MessengerEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerScreen(
    navController: NavController,
    state: MessengerUIState.Success,//TODO убрать Success
    onEvent: (MessengerEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()){
                TopAppBar(
                    title = {
                        Column() {
                            Text(
                                text = "Sample Company Name",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Inter,
                            )
                            Text(
                                text = "Vacancy",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = Inter,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Back",
                            )
                        }
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiaryContainer)
            }
        },
        bottomBar = {
            MessengerTextField(
                value = state.enteredText,
                onValueChange = { onEvent(MessengerEvent.SetMessage(input = it)) },

                )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(
                    listOfMessages
                ) { item ->
                    MessageBubble(item)
                }
            }
        }
    }
}


@Composable
private fun MessengerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(md_theme_dark_inverseOnSurface)
            .alpha(0.5f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(4.dp),
            shape = RoundedCornerShape(48.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Сообщение")
            }
        )
        IconButton(onClick = { /*TODO отправка сообщения*/ }) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Отправить"
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
private fun MessageBubble(message: MessageModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = if (message.isFromMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(if (message.isFromMe) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary)
                .padding(12.dp),
        ) {
            Text(text = message.text)
        }
    }
}

private val listOfMessages = listOf(
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
    MessageModel("Здравствуйте", User(id = "1")),
    MessageModel("Здравствуйте", User(id = "0")),
    MessageModel("Нас заинтересовало ваше резюме", User(id = "1")),
    MessageModel("Готов пройти стажировку у вас", User(id = "0")),
    MessageModel(
        "Хорошо, для этого придётся пройти отбор, напишите нам в телеграм в любое время",
        User(id = "1")
    ),
)
