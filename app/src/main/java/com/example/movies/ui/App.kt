package com.example.movies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movies.data.DataModule
import com.example.movies.domain.DomainModule
import com.example.movies.navigation.AppNavHost
import com.example.movies.presentation.PresentationModule
import com.example.movies.ui.theme.MoviesTheme
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(
        application = {
            androidLogger()
            modules(DataModule, DomainModule, PresentationModule)
        }
    ) {
        MoviesTheme {
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

