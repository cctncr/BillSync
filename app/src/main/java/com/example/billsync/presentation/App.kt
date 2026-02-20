package com.example.billsync.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.billsync.presentation.navigation.navhost.SetUpNavHost

@Composable
fun App() {
    val navController = rememberNavController()
    SetUpNavHost(navController = navController)
}