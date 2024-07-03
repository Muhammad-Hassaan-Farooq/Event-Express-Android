package com.example.eventexpress.auth.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.EventExpressTheme
import com.example.eventexpress.R
import com.example.eventexpress.auth.data.SignupUIState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier, changeScreen: () -> Unit,
    signupUIState: MutableStateFlow<SignupUIState>,
    handleSignUp:(fName: String, lName: String, email: String, password: String)->Unit,
) {

    val focusManager = LocalFocusManager.current

    val uiState = signupUIState.collectAsState()

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var firstName by rememberSaveable {
        mutableStateOf("")
    }

    var lastName by rememberSaveable {
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
                    text = stringResource(id = R.string.signup_header),
                    fontWeight = FontWeight.Bold,


                    )
                Text(
                    text = stringResource(id = R.string.signup_subtitle),
                    color = Color(0xFF505558)
                )
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
                    Text(
                        text = stringResource(id = R.string.first_name),
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    value = firstName, onValueChange = { firstName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.first_name_placeholder),

                            )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = when(uiState.value){
                            is SignupUIState.Error -> MaterialTheme.colorScheme.errorContainer
                            SignupUIState.Idle -> MaterialTheme.colorScheme.secondaryContainer
                            SignupUIState.Loading -> MaterialTheme.colorScheme.secondaryContainer
                            is SignupUIState.Success -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        unfocusedIndicatorColor =when(uiState.value){
                            is SignupUIState.Error -> MaterialTheme.colorScheme.errorContainer
                            SignupUIState.Idle -> MaterialTheme.colorScheme.secondaryContainer
                            SignupUIState.Loading -> MaterialTheme.colorScheme.secondaryContainer
                            is SignupUIState.Success -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer

                    ),

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
                        text = stringResource(id = R.string.last_name),
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    value = lastName, onValueChange = { lastName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.last_name_placeholder),

                            )
                    },

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),

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
                    Text(text = stringResource(id = R.string.email), fontWeight = FontWeight.Bold)
                }
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.email_placeholder),

                            )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),

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

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(10.dp), onClick = { handleSignUp(
                        firstName,
                        lastName,
                        email,
                        password
                    ) },
                    enabled = uiState.value==SignupUIState.Idle) {

                    when(uiState.value){
                        is SignupUIState.Error -> Text(
                            text = (uiState.value as SignupUIState.Error).message,
                            color = MaterialTheme.colorScheme.error

                        )
                        SignupUIState.Idle -> Text(
                            text = stringResource(id = R.string.sign_up),

                            )
                        SignupUIState.Loading -> CircularProgressIndicator()
                        is SignupUIState.Success -> {
                            Text(
                                text = stringResource(id = R.string.sign_up),
                            )
                            Toast.makeText(LocalContext.current, "Sign up successful", Toast.LENGTH_SHORT).show()
                            changeScreen()
                        }
                    }
                }
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.existing_account))
                    TextButton(onClick = changeScreen) {
                        Text(
                            text = stringResource(id = R.string.login_link),
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(textDecoration = TextDecoration.Underline)
                        )
                    }
                }
            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    EventExpressTheme {

    }
}