package android.employer_market.ui.screens.authentication.registration_screen

import android.app.Activity
import android.content.Intent
import android.employer_market.R
import android.employer_market.activities.AppActivity
import android.employer_market.ui.screens.custom_composables.RegistrationTextField
import android.employer_market.view_model.RegUIState
import android.employer_market.view_model.event.RegistrationEvent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CityCourseAndPhoneScreen(
    navController: NavController,
    onEvent: (RegistrationEvent) -> Unit,
    uiState: RegUIState.Success//TODO убрать Success
) {
    val localContext = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedCard(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 30.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegistrationTextField(
                value = uiState.city,
                onValueChange = {
                    onEvent(RegistrationEvent.SetCity(it))
                },
                label = stringResource(R.string.city),
                lastField = false
            )

            Button(
                onClick = {
                    onEvent(
                        RegistrationEvent.AddUser(
                            onSuccessAction = {
                                (localContext as Activity).finish()
                                localContext.startActivity(
                                    Intent(
                                        localContext,
                                        AppActivity::class.java
                                    )
                                )
                            },
                            onFailureAction = {
                                Toast.makeText(
                                    localContext,
                                    "Попробуйте ещё раз",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onEmptyPasswordAction = {
                                Toast.makeText(
                                    localContext,
                                    "Введите пароль",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onEmptyLoginAction = {
                                Toast.makeText(
                                    localContext,
                                    "Введите почту",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.next),
                )
            }
        }
    }
}