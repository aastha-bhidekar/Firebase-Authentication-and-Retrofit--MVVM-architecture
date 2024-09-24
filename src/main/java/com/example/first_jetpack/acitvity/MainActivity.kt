package com.example.first_jetpack.acitvity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.first_jetpack.AuthViewMode
import com.example.first_jetpack.screens.MainScreen
import com.example.first_jetpack.ui.theme.First_JetPackTheme
import com.example.first_jetpack.viewModel.AlbumViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewMode: AuthViewMode by viewModels()

        setContent {
            First_JetPackTheme {
                val navController = rememberNavController()
                val albumViewModel = remember {
                    AlbumViewModel()
                }
                MainScreen(navController = navController, authViewMode = authViewMode, albumViewModel)
            }
        }
    }
}
