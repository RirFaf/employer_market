package android.employer_market.ui.screens

import android.employer_market.R
import android.employer_market.network.models.ResumeFilter
import android.employer_market.network.models.VacancyModel
import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.navigation.extensions.noRippleClickable
import android.employer_market.ui.screens.custom_composables.ResumeCard
import android.employer_market.view_model.SearchUIState
import android.employer_market.view_model.event.FavouritesEvent
import android.employer_market.view_model.event.SearchEvent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUIState.Success,//TODO убрать Success
    navController: NavController,
    onEvent: (SearchEvent) -> Unit
) {
    val localContext = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = state.searchInput,
                        onValueChange = { onEvent(SearchEvent.SetSearchInput(it)) },
                        trailingIcon = {
                            IconButton(
                                onClick = { onEvent(SearchEvent.GetResumes) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(id = R.string.search),
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onEvent(SearchEvent.GetResumes)
                            }
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.search),
                            )
                        },
                        shape = RoundedCornerShape(32.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        singleLine = true
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(SearchEvent.ShowFilterDialog)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.filter_alt),
                            contentDescription = "filter",
                        )
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                itemsIndexed(
                    state.resumes
                ) { _, item ->
                    ResumeCard(
                        resume = item,
                        onClick = {
                            navController.navigate(
                                route = Screen.SelectedResumeScreen(
                                    id = item.id,
                                    studentId = item.studentId,
                                    salary = item.salary,
                                    keySkills = item.keySkills,
                                    secondName = item.secondName,
                                    firstName = item.firstName,
                                    patronymicName = item.patronymicName,
                                    birthDate = item.birthDate,
                                    university = item.university,
                                    institute = item.institute,
                                    course = item.course,
                                    aboutMe = item.aboutMe,
                                    gender = item.gender,
                                    city = item.city,
                                    direction = item.direction,
                                    liked = item.liked,
                                )
                            ) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onInvite = {
                            onEvent(SearchEvent.ShowVacancies)
                            onEvent(SearchEvent.SetChosenStudentId(input = item.studentId))
                            onEvent(SearchEvent.GetMyVacancies)
                        },
                        onLike = {
                            onEvent(SearchEvent.ChangeLiked(resumeId = item.id))
                        }
                    )
                }
            }
            if (state.showVacancies) {
                VacancyChoiceCard(
                    vacancies = state.myVacancies,
                    onChoice = { vacancy ->
                        onEvent(
                            SearchEvent.Invite(
                                vacancyId = vacancy.id,
                                studentId = state.chosenStudentId
                            )
                        )
                        onEvent(SearchEvent.ShowVacancies)
                    },
                    onDismiss = {
                        onEvent(SearchEvent.ShowVacancies)
                    }
                )
            }
            if (state.showFilterDialog) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCD000000))
                        .noRippleClickable {
                            onEvent(SearchEvent.ShowFilterDialog)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .noRippleClickable { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Выберите фильтр")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.currentFilter == ResumeFilter.None,
                                onClick = {
                                    onEvent(
                                        SearchEvent.SetResumesFilter(
                                            ResumeFilter.None
                                        )
                                    )
                                }
                            )
                            Text(
                                text = "Нет",
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.currentFilter != ResumeFilter.None,
                                    onClick = {
                                        onEvent(
                                            SearchEvent.SetResumesFilter(
                                                ResumeFilter.BySalary()
                                            )
                                        )
                                    }
                                )
                                Text(
                                    text = "Ожидаемая зарплата",
                                    textAlign = TextAlign.Center
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    value = state.from.toString(),
                                    onValueChange = {
                                        onEvent(
                                            SearchEvent.SetFrom(it)
                                        )
                                    },
                                    label = {
                                        Text(text = "От")
                                    },
                                    modifier = Modifier.fillMaxWidth(0.4f),
                                    enabled = state.currentFilter != ResumeFilter.None,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                                OutlinedTextField(
                                    value = state.to.toString(),
                                    onValueChange = {
                                        onEvent(
                                            SearchEvent.SetTo(it)
                                        )
                                    },
                                    label = {
                                        Text(text = "До")
                                    },
                                    modifier = Modifier.fillMaxWidth(0.8f),
                                    enabled = state.currentFilter != ResumeFilter.None,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                            }
                        }
                        Button(
                            onClick = {
                                onEvent(
                                    if (state.currentFilter != ResumeFilter.None) {
                                        SearchEvent.SetResumesFilter(
                                            ResumeFilter.BySalary(
                                                from = state.from,
                                                to = state.to
                                            )
                                        )
                                    } else {
                                        SearchEvent.SetResumesFilter(
                                            ResumeFilter.None
                                        )
                                    }
                                )
                                onEvent(
                                    SearchEvent.GetResumes
                                )
                                onEvent(SearchEvent.ShowFilterDialog)
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(text = "Готово")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VacancyChoiceCard(
    vacancies: List<VacancyModel>,
    onChoice: (VacancyModel) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { onDismiss() }
            .background(Color(0xCD000000))
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .noRippleClickable { }
        ) {
            if (vacancies.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Выберите вакансию для приглашения")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "cancel choice",
                            modifier = Modifier.noRippleClickable { onDismiss() }
                        )
                    }
                }
                LazyColumn {
                    itemsIndexed(vacancies) { _, vacancy ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(6.dp)
                            ) {
                                Text(text = vacancy.position)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = vacancy.salary.toString())
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = vacancy.edArea)
                                Spacer(modifier = Modifier.height(4.dp))
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { onChoice(vacancy) }
                                ) {
                                    Text(text = "Выбрать")
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Выберите вакансию для приглашения")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "cancel choice",
                                modifier = Modifier.noRippleClickable { onDismiss() }
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "У вас не добавлено ни одной вакансии")
                    }
                }
            }
        }
    }
}