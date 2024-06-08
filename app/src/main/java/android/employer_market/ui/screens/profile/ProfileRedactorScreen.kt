package android.employer_market.ui.screens.profile

import android.employer_market.ui.screens.custom_composables.CustomTextField
import android.employer_market.ui.screens.custom_composables.RegistrationTextField
import android.employer_market.view_model.ProfileUIState
import android.employer_market.view_model.event.ProfileEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRedactorScreen(
    navController: NavController,
    state: ProfileUIState.Success,//TODO убрать Success
    onEvent: (ProfileEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Редактор")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                heading = "Название",
                value = state.company.name,
                onValueChange = {
                    onEvent(ProfileEvent.SetName(it))
                },
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Возраст компании",
                value = state.company.age,
                onValueChange = {
                    onEvent(ProfileEvent.SetAge(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Сферы деятельности",
                value = state.company.profArea,
                onValueChange = {
                    onEvent(ProfileEvent.SetProfArea(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "О компаниии",
                value = state.company.about,
                onValueChange = {
                    onEvent(ProfileEvent.SetAbout(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Расположение, город",
                value = state.company.city,
                onValueChange = {
                    onEvent(ProfileEvent.SetCity(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                    onEvent(ProfileEvent.UpdateCompanyInfo)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}

