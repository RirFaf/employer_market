package android.employer_market.ui.screens.vacancy

import android.employer_market.ui.navigation.Screen
import android.employer_market.ui.screens.custom_composables.CustomText
import android.employer_market.view_model.ResumeUIState
import android.employer_market.view_model.event.ResumeEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    state: ResumeUIState.Success,//TODO убрать Success
    navController: NavController,
    onEvent: (ResumeEvent) -> Unit
) {
    val localContext = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.VacancyRedactorScreen) },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit resume")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp, vertical = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.size(100.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Icon(//TODO сменить на изображение
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "worker",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxSize(0.3f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Change profile picture"
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Яндекс Крауд", fontSize = 25.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Краткая информация",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
            CustomText(heading = "Должность", content = "Python-разработчик")
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(heading = "Города", content = "Казань, Москва")
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(heading = "Опыт", content = "Приветствуется")
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(heading = "Начиная", content = "С 3 курса")
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(heading = "Требуемое направление", content = "Прикладная информатика")
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(heading = "Код направления", content = "09.03.03")
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}