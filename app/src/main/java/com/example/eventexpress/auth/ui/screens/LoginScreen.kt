package com.example.eventexpress.auth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.EventExpressTheme
import com.example.eventexpress.R
import com.example.eventexpress.auth.data.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    changeScreen: ()->Unit,
    onSuccessfulLogin:()->Unit,
    loginState: MutableStateFlow<LoginUIState>
) {

    var uiState = loginState.collectAsState()

    println(uiState.toString())


    fun handleLogin(){

    }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }


    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_header),
                    fontWeight = FontWeight.Bold,


                    )
                Text(text = stringResource(id = R.string.login_subtitle), color = Color(0xFF505558))
            }

        }

        Row(modifier = Modifier.padding(2.dp)) {
            Divider(
                color = MaterialTheme.colorScheme.secondary, modifier = Modifier
                    .weight(0.48f)
                    .padding(2.dp)
            )
            Divider(
                color = Color.Transparent, modifier = Modifier
                    .weight(0.04f)
                    .padding(2.dp)
            )
            Divider(
                color = MaterialTheme.colorScheme.secondary, modifier = Modifier
                    .weight(0.48f)
                    .padding(2.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.email), fontWeight = FontWeight.Bold)
                }
                OutlinedTextField(
                    value = "", onValueChange = {}, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.email_placeholder),

                        )
                    },

                )
            }

            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.password),
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    trailingIcon = {
                        val image =
                            if (!showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),

                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.password_placeholder),
                            color = Color(0xFF898e92)
                        )
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),

                    )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 30.dp, top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(checked = false, onCheckedChange = {})
                            Text(text = stringResource(id = R.string.remember_me))

                        }
                        Row {
                            Text(text = stringResource(id = R.string.forgot_password))
                        }
                    }
                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { handleLogin() },
                        enabled = uiState.value == LoginUIState.Idle

                    ) {

                        when(uiState.value){
                            is LoginUIState.Error -> Text(
                                text = stringResource(id = R.string.sign_in),

                                )
                            LoginUIState.Idle -> Text(
                                text = stringResource(id = R.string.sign_in),

                                )
                            LoginUIState.Loading -> CircularProgressIndicator()
                            is LoginUIState.Success -> Text(
                                text = stringResource(id = R.string.sign_in),

                                )
                        }

                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.no_account))
                    TextButton(onClick = changeScreen) {
                        Text(text = stringResource(id = R.string.sign_up_link), fontWeight = FontWeight.Bold,style = TextStyle(textDecoration = TextDecoration.Underline))
                    }

                }
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EventExpressTheme {
        LoginScreen(modifier = Modifier, changeScreen = {}, onSuccessfulLogin = {},
            MutableStateFlow(LoginUIState.Idle)
        )
    }
}