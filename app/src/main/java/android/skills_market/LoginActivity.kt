package android.skills_market

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.skills_market.custom_composables.LoginPasswordAndButton
import android.skills_market.db_functions.SMFirebase
import android.skills_market.db_functions.isEmailValid
import android.skills_market.ui.theme.AccentBlue
import android.skills_market.ui.theme.ButtonColor
import android.skills_market.ui.theme.WhiteFontColor
import android.skills_market.users_dataclasses.Employer
import android.skills_market.users_dataclasses.Student
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class
LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoadUI(this)
        }
    }

    override fun onStart() {
        super.onStart()
        val database = SMFirebase()
//        database.authentication(this)
    }
}

@Composable
private fun LoadUI(localContext: Context) {
    var openDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 160.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_logo_dev_24),
            contentDescription = stringResource(id = R.string.logo_description),
            tint = AccentBlue
        )
        LoginPasswordAndButton(localContext = localContext)
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.help)),
//            style = ,
            onClick = { offset ->
                Log.d("ClickableText", "$offset -th character is clicked.")
            }
        )
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.registration)),
            onClick = {
                openDialog = !openDialog
            }
        )
        LoadPopUp(openDialog = openDialog, localContext)
    }
}

@Composable
private fun LoadPopUp(openDialog: Boolean, localContext: Context) {
    Box {
        val popupWidth = 300.dp
        val popupHeight = 100.dp

        if (openDialog) {

            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties()
            ) {
                Box(
                    modifier = Modifier
                        .size(popupWidth, popupHeight)
                        .padding(top = 6.dp)
                        .background(
                            color = Color.Gray,
                            RoundedCornerShape(10.dp)
                        )
                        .border(2.dp, color = Color.Black, RoundedCornerShape(10.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                localContext.startActivity(
                                    Intent(
                                        localContext,
                                        StudentRegisterActivity::class.java
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                        ) {
                            Text(
                                text = stringResource(id = R.string.student),
                                color = WhiteFontColor
                            )
                        }

                        Button(
                            onClick = {
                                localContext.startActivity(
                                    Intent(
                                        localContext,
                                        EmployerRegisterActivity::class.java
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                        ) {
                            Text(
                                text = stringResource(id = R.string.employer),
                                color = WhiteFontColor
                            )
                        }
                    }
                }
            }
        }
    }
}


