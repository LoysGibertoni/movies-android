package com.example.movies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movies.navigation.AppNavHost
import com.example.movies.ui.theme.MoviesTheme
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun App() {
    MoviesTheme {
        KoinAndroidContext {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar(navController)
                },
                content = { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        navController = navController
                    )
                }
            )
        }
    }
}

