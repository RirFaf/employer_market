package android.employer_market.ui.screens.custom_composables

import android.employer_market.data.constants.ResponseStatus
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResponseCard(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
    onApproveButtonClick: () -> Unit,
    onDenyButtonClick: () -> Unit,
    onChatButtonClick: () -> Unit,
    onInviteButtonClick: () -> Unit,
    resume: Pair<ResumeModel, VacancyModel>,
    status: String
) {
    OutlinedCard(
        modifier = modifier
            .clickable {
                onCardClick()
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
                text = "${resume.first.secondName} ${resume.first.firstName} ${resume.first.patronymicName} ",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = when (status) {
                    ResponseStatus.INVITE -> "Приглашён на вакансию"
                    ResponseStatus.DENIED -> "Откликался на вакансию"
                    else -> "Откликается на вакансию"
                },
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (status == ResponseStatus.INVITE) {
                    Color.Green
                } else {
                    Color.Unspecified
                }
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = resume.second.position,
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (status == ResponseStatus.SENT || status == ResponseStatus.APPROVED) {
                    Button(
                        onClick = {
                            if (status == ResponseStatus.SENT) {
                                onApproveButtonClick()
                            } else {
                                onChatButtonClick()
                            }
                        },
                    ) {
                        Text(
                            text = if (status == ResponseStatus.SENT) {
                                "Одобрить"
                            } else {
                                "Перейти в чат"
                            }
                        )
                    }
                    Button(
                        onClick = {
                            if (status == ResponseStatus.SENT) {
                                onDenyButtonClick()
                            } else {
                                onInviteButtonClick()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (status == ResponseStatus.SENT) {
                                "Отклонить"
                            } else {
                                "Закрыть чат"
                            }
                        )
                    }
                } else if (status == ResponseStatus.DENIED) {
                    Text(text = "Отклонено", color = Color.Red)
                }
            }
        }
    }
}