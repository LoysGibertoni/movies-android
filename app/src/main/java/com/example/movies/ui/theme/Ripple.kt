@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movies.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable


@Composable
fun primaryRippleConfiguration() = RippleConfiguration(
    MaterialTheme.colorScheme.onPrimary, RippleAlpha(
        draggedAlpha = 0.64f,
        focusedAlpha = 0.4f,
        hoveredAlpha = 0.32f,
        pressedAlpha = 0.4f
    )
)