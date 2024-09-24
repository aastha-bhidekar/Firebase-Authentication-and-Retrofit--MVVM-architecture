package com.example.first_jetpack.screens
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.first_jetpack.AuthState
import com.example.first_jetpack.AuthViewMode
import com.example.first_jetpack.navigation.Routes
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, authViewMode: AuthViewMode) {
    val context = LocalContext.current
    val authState = authViewMode.authState.observeAsState()
    val username = authViewMode.username.observeAsState()

    Log.d("ProfileScreen", "AuthState: ${authState.value}")
    Log.d("ProfileScreen", "Username: ${username.value}")

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(Routes.login)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.surface)
                .border(2.dp, MaterialTheme.colors.onSurface, CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = "Profile Picture", modifier = Modifier.size(96.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Information
        Text(
            text = username.value ?: "No Username",  // Display username or a default message
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign-Out Button
        Button(
            onClick = {
                authViewMode.signout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
    }
}
