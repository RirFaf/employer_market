package android.employer_market.ui.screens.authentication.registration_screen

import android.employer_market.R
import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.screens.custom_composables.RegistrationTextField
import android.employer_market.view_model.RegUIState
import android.employer_market.view_model.event.RegistrationEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun CompanyInfoScreen(
    navController: NavController,
    onEvent: (RegistrationEvent) -> Unit,
    uiState: RegUIState.Success//TODO убрать Success
) {
    var isCityWrong by remember {
        mutableStateOf(false)
    }
    var isCompanyWrong by remember {
        mutableStateOf(false)
    }
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
                value = uiState.companyName,
                onValueChange = {
                    onEvent(RegistrationEvent.SetCompanyName(it))
                    isCompanyWrong = false
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next
                ),
                label = stringResource(R.string.company_name),
                isError = isCompanyWrong
            )
            RegistrationTextField(
                value = uiState.city,
                onValueChange = {
                    onEvent(RegistrationEvent.SetCity(it))
                    isCityWrong = false
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (
                            uiState.companyName.isNotBlank() &&
                            uiState.city.isNotBlank()
                        ) {
                            navController.navigate(Screen.EmailAndPasswordScreen.route)
                        }
                        if (uiState.city.isBlank()) {
                            isCityWrong = true
                        }
                        if (uiState.companyName.isBlank()) {
                            isCompanyWrong = true
                        }
                    }
                ),
                label = "Город",
                isError = isCityWrong
            )

            Button(
                onClick = {
                    if (
                        uiState.companyName.isNotBlank() &&
                        uiState.city.isNotBlank()
                    ) {
                        navController.navigate(Screen.EmailAndPasswordScreen.route)
                    }
                    if (uiState.city.isBlank()) {
                        isCityWrong = true
                    }
                    if (uiState.companyName.isBlank()) {
                        isCompanyWrong = true
                    }
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