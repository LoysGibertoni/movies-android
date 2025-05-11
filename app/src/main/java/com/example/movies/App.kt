package com.example.movies

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.movies.presentation.theme.MoviesTheme
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun App() {
    MoviesTheme {
        KoinAndroidContext {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    val activity = LocalActivity.current
                    TopBar {
                        if (!navController.popBackStack()) {
                            activity?.finish()
                        }
                    }
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

