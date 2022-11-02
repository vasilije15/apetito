package com.vaske.restaurants.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.vaske.restaurants.util.restaurantDetailDummyData
import org.junit.Rule
import org.junit.Test

class RestaurantDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun restaurant_name_type_desc_are_shown() {
        composeTestRule.setContent {
            RestaurantDetails(restaurantDetails = restaurantDetailDummyData)
        }

        composeTestRule.onNodeWithText(restaurantDetailDummyData.name).assertExists()
        composeTestRule.onNodeWithText(restaurantDetailDummyData.type).assertExists()
        composeTestRule.onNodeWithContentDescription("Restaurant image").assertExists()

    }

    @Test
    fun restaurant_description_is_expanded() {
        composeTestRule.setContent {
            RestaurantDetails(restaurantDetails = restaurantDetailDummyData)
        }

        composeTestRule.restaurantDescription().run {
            assertHasClickAction()
            performClick()
            assertHeightIsAtLeast(150.dp)
        }
    }

    @Test
    fun down_arrow_is_removed_when_content_is_expanded() {
        composeTestRule.setContent {
            RestaurantDetailsScreen(restaurantDetails = restaurantDetailDummyData)
        }

        composeTestRule.restaurantDescription()
            .assertExists()
            .assertHasClickAction()
            .performClick()


        composeTestRule.restaurantOpenHours()
            .assertExists()
            .assertHasClickAction()
            .performClick()

        composeTestRule.restaurantReview()
            .assertExists()
            .assertHasClickAction()
            .performScrollTo()
            .performClick()


        composeTestRule
            .onNodeWithContentDescription("Expand icon Outlined.KeyboardArrowDown")
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.restaurantDescription(): SemanticsNodeInteraction =
        this.onNodeWithContentDescription("Front icon Outlined.Info")

    private fun ComposeContentTestRule.restaurantOpenHours(): SemanticsNodeInteraction =
        this.onNodeWithContentDescription("Front icon Outlined.Schedule")

    private fun ComposeContentTestRule.restaurantReview(): SemanticsNodeInteraction =
        this.onNodeWithContentDescription("Front icon Outlined.StarOutline")
}