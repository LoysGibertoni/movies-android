package com.example.movies.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.movies.presentation.R

@Composable
internal fun FullScreenError(
    message: String,
    button: FullScreenErrorButton? = null
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(R.dimen.fullscreen_error_icon_size)),
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.space_medium)),
            text = message,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        button?.let {
            ElevatedButton(
                onClick = it.onClick,
            ) {
                Text(it.text)
            }
        }
    }
}

internal data class FullScreenErrorButton(
    val text: String,
    val onClick: () -> Unit
)