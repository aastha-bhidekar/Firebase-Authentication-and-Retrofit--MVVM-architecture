package com.example.first_jetpack.screens

import com.example.first_jetpack.R
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.first_jetpack.AuthState
import com.example.first_jetpack.AuthViewMode
import com.example.first_jetpack.navigation.Routes


@Composable
fun SignupScreen(navController: NavController, authViewMode: AuthViewMode){

    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isUsernameValid = remember { mutableStateOf(true) }
    val isEmailValid = remember { mutableStateOf(true) }
    val isPasswordValid = remember { mutableStateOf(true) }
    val context = LocalContext.current

    val authState = authViewMode.authState.observeAsState()


    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Routes.home)
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error ).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Login Image",
                Modifier.size(30.dp)
            )

            Text(
                text = stringResource(R.string.Title),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.Signup_Title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.Subtitle),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.User_name),
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = {
                username.value = it
                isUsernameValid.value = it.isNotBlank() // Ensure username is not empty
            },
            label = { Text(text = "John Loe") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            },
            shape = RoundedCornerShape(16.dp)
        )

        if (!isUsernameValid.value) {
            Text(
                text = stringResource(R.string.empty_user),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = stringResource(R.string.email),
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text(text = "example@gmail.com") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            },
            shape = RoundedCornerShape(16.dp)
        )

        if (!isEmailValid.value) {
            Text(
                text = stringResource(id = R.string.invalid_email),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.password),
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                isPasswordValid.value = it.length >= 8
            },
            label = { Text(text = "*********") },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password Icon"
                )
            },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        if (!isPasswordValid.value) {
            Text(
                text = stringResource(id = R.string.password_length),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.value.isNotEmpty() && password.value.isNotEmpty() && username.value.isNotEmpty()
                    && isUsernameValid.value && isEmailValid.value && isPasswordValid.value)
                     {
                        authViewMode.signup(email.value,password.value, username.value)
                } else {
                    // Show errors if necessary
                    if (username.value.isEmpty()) {
                        Toast.makeText(context, "Username is empty", Toast.LENGTH_SHORT).show()
                    }
                    if (email.value.isEmpty()) {
                        Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show()
                    }
                    if(password.value.isEmpty()){
                        Toast.makeText(context,"Password is empty", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sign Up")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Sign In Icon"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Already have an account?")
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Sign In",
                modifier = Modifier
                    .clickable{ navController.navigate(Routes.login)},
                style = MaterialTheme.typography.button.copy(color = Color.Blue)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(3.dp, Color.Blue), RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Image",
                    Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.google_login),
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}






//@Preview(showBackground = true)
//@Composable
//fun SignupPreview() {
//    First_JetPackTheme {
//        val navController = rememberNavController()
//        SignupScreen(navController)
//    }
//}




