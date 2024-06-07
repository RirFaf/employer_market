package android.employer_market.ui.screens.custom_composables

import android.employer_market.R
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumeCard(
    resume: ResumeModel,
    onClick: () -> Unit,
    onRespond:()->Unit,
    onLike:()->Unit,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current
    var enabled by remember {
        mutableStateOf(true)
    }
    var liked by remember {
        mutableStateOf(resume.liked)
    }
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
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 14.dp)
        ) {
            Text(
                text = resume.direction,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = resume.salary.ifBlank { "Не указано" },
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "${resume.secondName} ${resume.firstName} ${resume.patronymicName} ",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        Toast.makeText(localContext, "Work in progress", Toast.LENGTH_SHORT)
                            .show()
                        enabled = false
                    },
                ) {
                    Text(text = stringResource(id = R.string.respond))
                }
                IconButton(
                    onClick = {
                        Toast.makeText(localContext, "Work in progress", Toast.LENGTH_SHORT)
                            .show()
                        liked = !liked
                    }
                ) {
                    Icon(
                        if (liked) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = ""
                    )
                }
            }
        }
    }
}