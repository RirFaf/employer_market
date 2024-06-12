package android.employer_market.ui.screens

import android.employer_market.data.constants.ResponseStatus
import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.screens.custom_composables.ResponseCard
import android.employer_market.view_model.ResponsesUIState
import android.employer_market.view_model.event.ResponsesEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsesListScreen(
    navController: NavController,
    state: ResponsesUIState.Success,
    onEvent: (ResponsesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Отклики") },
                actions = {
//                    IconButton(
//                        onClick = {}
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.Notifications,
//                            contentDescription = "Show menu",
//                        )
//                    }
//                    IconButton(
//                        onClick = { }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Menu,
//                            contentDescription = "Show menu",
//                        )
//                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                itemsIndexed(
                    state.responses
                ) { _, item ->
                    ResponseCard(
                        resume = item,
                        onCardClick = {
                            navController.navigate(
                                route = Screen.SelectedResumeScreen(
                                    id = item.first.id,
                                    studentId = item.first.studentId,
                                    salary = item.first.salary,
                                    keySkills = item.first.keySkills,
                                    secondName = item.first.secondName,
                                    firstName = item.first.firstName,
                                    patronymicName = item.first.patronymicName,
                                    birthDate = item.first.birthDate,
                                    university = item.first.university,
                                    institute = item.first.institute,
                                    course = item.first.course,
                                    aboutMe = item.first.aboutMe,
                                    gender = item.first.gender,
                                    city = item.first.city,
                                    direction = item.first.direction,
                                    liked = item.first.liked,
                                )
                            ) {
                                launchSingleTop = false
                                restoreState = true
                            }
                        },
                        onChatButtonClick = {
                            onEvent(
                                ResponsesEvent.AddChat(
                                    vacancyId = item.second.id,
                                    studentId = item.first.studentId
                                )
                            )
                            navController.navigate(Screen.MessengerScreen)
                        },
                        onApproveButtonClick = {
                            onEvent(
                                ResponsesEvent.UpdateResponseStatus(
                                    studentId = item.first.studentId,
                                    vacancyId = item.second.id,
                                    status = ResponseStatus.APPROVED
                                )
                            )
                        },
                        onDenyButtonClick = {
                            onEvent(
                                ResponsesEvent.UpdateResponseStatus(
                                    studentId = item.first.studentId,
                                    vacancyId = item.second.id,
                                    status = ResponseStatus.DENIED
                                )
                            )
                        },
                        onInviteButtonClick = {
                            onEvent(
                                ResponsesEvent.UpdateResponseStatus(
                                    studentId = item.first.studentId,
                                    vacancyId = item.second.id,
                                    status = ResponseStatus.INVITE
                                )
                            )
                        },
                        status = item.first.responseStatus
                    )
                }
            }
        }
    }
}


