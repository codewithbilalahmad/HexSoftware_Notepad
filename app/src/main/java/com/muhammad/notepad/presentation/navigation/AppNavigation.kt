package com.muhammad.notepad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammad.notepad.presentation.screens.home.HomeScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen){
        composable<Destinations.HomeScreen>{
            HomeScreen()
        }
        composable<Destinations.SettingsScreen>{  }
    }
}