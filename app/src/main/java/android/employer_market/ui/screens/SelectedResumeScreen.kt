package android.employer_market.ui.screens

import android.employer_market.R
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.employer_market.ui.screens.custom_composables.CustomText
import android.employer_market.ui.theme.Inter
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedResumeScreen(navController: NavController, resume: ResumeModel) {
    val localContext = LocalContext.current
    var liked by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Вакансия") },
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
                },
                actions = {
                    IconButton(
                        onClick = {
//                            Toast.makeText(localContext, "Work in progress", Toast.LENGTH_SHORT)
//                                .show()
                            liked = !liked
                        }
                    ) {
                        Icon(
                            imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Show menu",
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${resume.secondName} ${resume.firstName} ${resume.patronymicName} ",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Inter,
                )
            }
            CustomText(heading = "Направление", content = resume.direction)
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Университет", content = resume.university)
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Институт", content = resume.institute)
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Курс", content = resume.course)
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Расположение", content = resume.city)
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Ключевые навыки", content = resume.keySkills.ifBlank { "Не указано" })
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "Ожидаемая заработная плата", content = resume.salary.ifBlank { "Не указано" })
            Spacer(modifier = Modifier.padding(8.dp))
            CustomText(heading = "О соискателе", content = resume.aboutMe.ifBlank { "Не указано" })
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(localContext, "Work in progress", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                Text(text = "Откликнуться")
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}