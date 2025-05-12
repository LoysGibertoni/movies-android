package com.example.movies

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesTest {

    @get:Rule
    val composeRule = createEmptyComposeRule()

    private val robot = MoviesRobot(composeRule)

    @Test
    fun testSearchMovieAndOpenDetails() = robot {
        val search = "Fast"
        val movieTitle = "The Fast and the Furious"

        search(search)
        assertMovieIsDisplayed(movieTitle)
        clickMovie(movieTitle)
        assertDetailsAreDisplayed(movieTitle)
    }
}