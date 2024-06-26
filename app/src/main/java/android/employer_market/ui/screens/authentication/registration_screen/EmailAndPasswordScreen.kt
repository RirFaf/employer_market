package android.employer_market.ui.screens.authentication.registration_screen

import android.content.Intent
import android.employer_market.R
import android.employer_market.activities.AppActivity
import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.screens.custom_composables.RegistrationTextField
import android.employer_market.view_model.RegUIState
import android.employer_market.view_model.event.RegistrationEvent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailAndPasswordScreen(
    navController: NavController,
    onEvent: (RegistrationEvent) -> Unit,
    uiState: RegUIState.Success//TODO убрать Success
) {
    val localContext = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var isPasswordWrong by remember {
        mutableStateOf(false)
    }
    var isLoginWrong by remember {
        mutableStateOf(false)
    }
    OutlinedCard(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegistrationTextField(
                value = uiState.email,
                onValueChange = {
                    isLoginWrong = false
                    onEvent(RegistrationEvent.SetEmail(it))
                },
                label = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = isLoginWrong
            )
            OutlinedTextField(
                value = uiState.password1,
                onValueChange = {
                    isPasswordWrong = false
                    onEvent(RegistrationEvent.SetPassword1(it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.password)) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ),
                isError = isPasswordWrong,
                trailingIcon = {
                    //Изменеие видимости пароля
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (passwordVisible)
                                    R.drawable.visibility
                                else
                                    R.drawable.visibility_off
                            ),
                            contentDescription = "password visibility",
                        )
                    }
                },
                shape = MaterialTheme.shapes.medium,
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = {
                    isPasswordWrong = false
                    onEvent(RegistrationEvent.SetPassword(it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.password)) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                isError = isPasswordWrong,
                trailingIcon = {
                    //Изменеие видимости пароля
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (passwordVisible)
                                    R.drawable.visibility
                                else
                                    R.drawable.visibility_off
                            ),
                            contentDescription = "password visibility",
                        )
                    }
                },
                shape = MaterialTheme.shapes.medium,
                keyboardActions = KeyboardActions(
                    onDone = {
                        RegistrationEvent.AddUser(
                            onSuccessAction = {
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
                                isPasswordWrong = true
                            },
                            onEmptyLoginAction = {
                                isLoginWrong = true
                            }
                        )
                    }
                ),
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                onClick = {
                    onEvent(RegistrationEvent.AddUser(
                        onSuccessAction = {
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
                            isPasswordWrong = true
                        },
                        onEmptyLoginAction = {
                            isLoginWrong = true
                        }
                    ))
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