package com.example.movies

import android.content.Context
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
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movies.mock.startMockServer
import com.example.movies.presentation.R

@OptIn(ExperimentalTestApi::class)
class MoviesRobot(
    private val composeRule: ComposeTestRule
) {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    operator fun invoke(block: MoviesRobot.() -> Unit) {
        startMockServer(MoviesMocks)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        block(this)
        scenario.close()
    }

    fun search(title: String) {
        composeRule.onNodeWithContentDescription(context.getString(R.string.search_field_content_description)).performTextInput(title)
    }

    fun assertMovieIsDisplayed(title: String) {
        val movieTitle = hasContentDescription(context.getString(R.string.title_content_description)).and(hasText(title))
        composeRule.waitUntilAtLeastOneExists(movieTitle)
        composeRule.onNode(movieTitle).assertIsDisplayed()
        // ... validar outros campos
    }

    fun clickMovie(title: String) {
        composeRule.onNode(hasContentDescription(context.getString(R.string.title_content_description)).and(hasText(title))).performClick()
    }

    fun assertDetailsAreDisplayed(title: String) {
        val movieTitle = hasContentDescription(context.getString(R.string.title_content_description)).and(hasText(title))
        composeRule.onNodeWithText("Detalhes").assertIsDisplayed()
        composeRule.onNode(movieTitle).assertIsDisplayed()
        // ... validar outros campos
    }
}