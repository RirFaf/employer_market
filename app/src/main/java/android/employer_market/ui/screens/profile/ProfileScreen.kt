package android.employer_market.ui.screens.profile

import android.app.Activity
import android.content.Intent
import android.employer_market.R
import android.employer_market.activities.LogRegActivity
import android.employer_market.data.repository.SMFirebase
import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.screens.custom_composables.CustomText
import android.employer_market.view_model.ProfileUIState
import android.employer_market.view_model.event.ProfileEvent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    state: ProfileUIState.Success,//TODO убрать Success
    onEvent: (ProfileEvent) -> Unit
) {
    val localContext = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Профиль") },
                actions = {
                    val database = SMFirebase
//                    IconButton(
//                        onClick = {
//                            Toast.makeText(localContext, "Work in progress", Toast.LENGTH_SHORT)
//                                .show()
//                        },
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.notifications_none),
//                            contentDescription = "Show menu",
//                        )
//                    }
                    IconButton(
                        onClick = {
                            //TODO засунусть в обработчик событий
                            database.logoutUser(
                                onLogoutAction = {
                                    (localContext as Activity).finish()
                                    localContext.startActivity(
                                        Intent(
                                            localContext,
                                            LogRegActivity::class.java
                                        )
                                    )
                                }
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ExitToApp,
                            contentDescription = "Logout",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ProfileRedactorScreen) },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit profile")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(//TODO сменить на изображение
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "worker",
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = state.company.name, fontSize = 26.sp)
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    navController.navigate(Screen.VacanciesListScreen)
                },
            ) {
                Text(
                    text = stringResource(R.string.my_resume),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "О компании:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

            }
            Text(
                text = state.company.about.ifBlank { "Не указано" },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (state.company.profArea.isNotBlank()) Color.Unspecified else Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                heading = "На рынке ", content = state.company.age.ifBlank { "Не указано" },
                color = if (state.company.age.isNotBlank()) Color.Unspecified else Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                heading = "Сферы деятельности",
                content = state.company.profArea.ifBlank { "Не указано" },
                color = if (state.company.profArea.isNotBlank()) Color.Unspecified else Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

