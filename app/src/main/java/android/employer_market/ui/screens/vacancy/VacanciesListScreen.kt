package android.employer_market.ui.screens.vacancy

import android.employer_market.network.models.VacancyModel
import android.employer_market.ui.navigation.Screen
import android.employer_market.view_model.VacancyUIState
import android.employer_market.view_model.event.VacancyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacanciesListScreen(
    state: VacancyUIState.Success,
    navController: NavController,
    onEvent: (VacancyEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Выберите вакансию")
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(VacancyEvent.SetVacancy(VacancyModel()))
                            onEvent(VacancyEvent.SetIsCurrentVacancyNew(true))
                            navController.navigate(Screen.VacancyRedactorScreen)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "add vacancy")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screen.ProfileScreen) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.vacancies.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    itemsIndexed(
                        state.vacancies
                    ) { _, item ->
                        VacancyCard(
                            vacancy = item,
                            onClick = {
                                onEvent(VacancyEvent.SetVacancy(item))
                                onEvent(VacancyEvent.SetIsCurrentVacancyNew(false))
                                navController.navigate(Screen.VacancyRedactorScreen)
                            },
                            onDelete = {
                                onEvent(VacancyEvent.DeleteVacancy(item))
                                onEvent(VacancyEvent.GetVacancies)
                            }
                        )
                    }
                }
            } else {
                Button(
                    onClick = {
                        onEvent(VacancyEvent.SetVacancy(VacancyModel()))
                        onEvent(VacancyEvent.SetIsCurrentVacancyNew(true))
                        navController.navigate(Screen.VacancyRedactorScreen)
                    }
                ) {
                    Text(text = "Добавить вакансию")
                }
            }
        }
    }
}

@Composable
private fun VacancyCard(
    vacancy: VacancyModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .clickable {
                onClick()
            },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = vacancy.position,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = vacancy.salary.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = vacancy.edArea,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 14.sp
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onDelete() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete button")
                }
            }
        }
    }
}