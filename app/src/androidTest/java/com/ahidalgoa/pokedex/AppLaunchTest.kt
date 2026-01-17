package com.ahidalgoa.pokedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppLaunchTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun appLaunches_andPokemonListIsDisplayed() {
        // The ViewModel loads data from the repository in its init block.
        // Our TestAppModule provides the FakePokemonRepository, which has a default list.
        // We just need to verify that a pokemon from that list is displayed.
        composeTestRule.onNodeWithText("bulbasaur").assertIsDisplayed()
    }
}
