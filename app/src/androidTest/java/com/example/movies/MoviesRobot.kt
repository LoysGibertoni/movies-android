package com.example.movies

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import com.example.movies.mock.startMockServer

@OptIn(ExperimentalTestApi::class)
class MoviesRobot(
    private val composeRule: ComposeTestRule
) {

    operator fun invoke(block: MoviesRobot.() -> Unit) {
        startMockServer(MoviesMocks)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        block(this)
        scenario.close()
    }

    fun search(title: String) {
        composeRule.onNodeWithContentDescription("Campo de texto para busca de filmes por título").performTextInput(title)
    }

    fun assertMovieIsDisplayed(title: String) {
        val movieTitle = hasContentDescription("Título do filme").and(hasText(title))
        composeRule.waitUntilAtLeastOneExists(movieTitle)
        composeRule.onNode(movieTitle).assertIsDisplayed()
        // ... validar outros campos
    }

    fun clickMovie(title: String) {
        composeRule.onNode(hasContentDescription("Título do filme").and(hasText(title))).performClick()
    }

    fun assertDetailsAreDisplayed(title: String) {
        val movieTitle = hasContentDescription("Título do filme").and(hasText(title))
        composeRule.onNodeWithText("Detalhes").assertIsDisplayed()
        composeRule.onNode(movieTitle).assertIsDisplayed()
        // ... validar outros campos
    }
}