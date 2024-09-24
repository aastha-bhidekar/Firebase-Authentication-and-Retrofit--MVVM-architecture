package com.example.first_jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.first_jetpack.AuthState
import com.example.first_jetpack.AuthViewMode
import com.example.first_jetpack.navigation.BottomNavigationBar
import com.example.first_jetpack.navigation.Routes
import com.example.first_jetpack.navigation.TopBar
import com.example.first_jetpack.viewModel.AlbumViewModel


@Composable
fun MainScreen(navController: NavHostController, authViewMode: AuthViewMode, albumViewModel: AlbumViewModel) {

    val authState = authViewMode.authState.observeAsState()

    Scaffold(
        topBar = {
            if (authState.value is AuthState.Authenticated) {
                TopBar(title = "My App", onMenuClick = { /* Handle menu click */ })
            }
        },
        bottomBar = {
            if (authState.value is AuthState.Authenticated) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = getStartDestination(authState.value), Modifier.padding(innerPadding)) {
            composable(Routes.home) { HomeScreen(navController, authViewMode, albumViewModel) }
            composable(Routes.profile) { ProfileScreen(navController,authViewMode) }
            composable(Routes.search) { SearchScreen(navController) }
            composable("movieDetail/{albumId}") { backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")
                MovieDetailScreen(albumId, albumViewModel)
            }
            composable(Routes.login) { LoginScreen(navController, authViewMode) }
            composable(Routes.signup) { SignupScreen(navController, authViewMode) }
            composable(Routes.splashScreen) { SplashScreen(navController) }
        }
    }
}

@Composable
fun getStartDestination(authState: AuthState?): String {
    Log.d("MainScreen2", "AuthState: $authState")
    return when (authState) {
        is AuthState.Authenticated -> Routes.home
        is AuthState.Unauthenticated -> Routes.login
        else -> Routes.splashScreen // or some default route
    }
}

