package android.employer_market.ui.screens.messenger

import android.employer_market.R
import android.employer_market.network.models.MessageModel
import android.employer_market.ui.theme.Inter
import android.employer_market.view_model.MessengerUIState
import android.employer_market.view_model.event.MessengerEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Column() {
                            Text(
                                text = "${state.currentStudentName.split(" ")[0]} ${state.currentStudentName.split(" ")[1].first()}.",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Inter,
                            )
                            Text(
                                text = state.currentVacancyName,
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
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = state.enteredText,
                    onValueChange = { onEvent(MessengerEvent.SetMessage(input = it)) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(4.dp),
                    shape = RoundedCornerShape(48.dp),
                    placeholder = {
                        Text(text = "Сообщение")
                    }
                )
                IconButton(
                    onClick = {
                        onEvent(MessengerEvent.SendMessage)
                        onEvent(MessengerEvent.SetMessage(input = ""))
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Отправить"
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(innerPadding),
            reverseLayout = true,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                state.messages.asReversed()
            ) { index, item ->
                Column {
                    if (index == state.messages.lastIndex ||
                        state.messages.asReversed()[index].timestamp.subSequence(0, 10) !=
                        state.messages.asReversed()[index + 1].timestamp.subSequence(0, 10)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.timestamp.subSequence(0, 10).toString())
                        }
                    }
                    MessageBubble(message = item, isFromMe = item.senderId == state.currentUserid)
                }
            }
        }
    }
}
@Composable
private fun MessageBubble(message: MessageModel, isFromMe: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (isFromMe) 48f else 0f,
                        bottomEnd = if (isFromMe) 0f else 48f
                    )
                )
                .background(if (isFromMe) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary)
                .padding(12.dp),
        ) {
            Text(text = message.text)
        }
    }
}


@Composable
fun StudentItem(){
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Имя", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Возраст", style = MaterialTheme.typography.displayMedium)
        }
    }
}

