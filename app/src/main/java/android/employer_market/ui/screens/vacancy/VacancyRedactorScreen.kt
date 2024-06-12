package android.employer_market.ui.screens.vacancy

import android.employer_market.ui.screens.custom_composables.CustomTextField
import android.employer_market.view_model.VacancyUIState
import android.employer_market.view_model.event.VacancyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
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
fun VacancyRedactorScreen(
    state: VacancyUIState.Success,//TODO убрать Success
    navController: NavController,
    onEvent: (VacancyEvent) -> Unit
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
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
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
                heading = "Область подготовки",
                value = state.currentVacancy.edArea,
                onValueChange = {
                    onEvent(VacancyEvent.SetEdArea(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Формат занятости",
                value = state.currentVacancy.formOfEmployment,
                onValueChange = {
                    onEvent(VacancyEvent.SetFormOfEmployment(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Назвние вакансии",
                value = state.currentVacancy.position,
                onValueChange = {
                    onEvent(VacancyEvent.SetPosition(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Требования",
                value = state.currentVacancy.requirements,
                onValueChange = {
                    onEvent(VacancyEvent.SetRequirements(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomTextField(
                heading = "Зарплата",
                value = state.currentVacancy.salary.toString(),
                onValueChange = {
                    onEvent(VacancyEvent.SetSalary(it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                    onEvent(VacancyEvent.A)
                    onEvent(VacancyEvent.GetVacancies)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Сохранить")
            }
        }
    }

}