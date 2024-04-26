package com.flybuys.newsapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.flybuys.newsapp.model.Enclosure
import com.flybuys.newsapp.model.Feed
import com.flybuys.newsapp.model.GenericResponse
import com.flybuys.newsapp.model.NewsItem
import com.flybuys.newsapp.model.NewsItems
import com.flybuys.newsapp.repo.INewsRepo
import com.flybuys.newsapp.ui.screens.feed.NewsFeedActivity
import com.flybuys.newsapp.ui.screens.feed.NewsFeedViewModel
import com.flybuys.newsapp.ui.screens.feed.components.NewsFeedLayout
import com.flybuys.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TestNewsFeedLayout {

    @get:Rule
    val composeTestRule = createComposeRule()

    class FakeRepo : INewsRepo {
        override suspend fun getNewsItems(): Flow<GenericResponse> {
            val newsItems = NewsItems(
                "ok", Feed("title", "image"), arrayOf(
                    NewsItem(
                        "Top News",
                        "24 May 2024 04 AM",
                        "",
                        "1",
                        "https://live-production.wcms.abc-cdn.net.au/29ff1c371714562351ce7c9cbe0cee64?impolicy=wcms_crop_resize&amp;cropH=3333&amp;cropW=2500&amp;xPos=1250&amp;yPos=0&amp;width=862&amp;height=1149",
                        "Description",
                        "This is where we see some news, blah blah",
                        Enclosure(
                            thumbnail = "",
                            link = "https://live-production.wcms.abc-cdn.net.au/29ff1c371714562351ce7c9cbe0cee64?impolicy=wcms_crop_resize&amp;cropH=3333&amp;cropW=2500&amp;xPos=1250&amp;yPos=0&amp;width=862&amp;height=1149"
                        ),
                        arrayOf("a")
                    ),
                    NewsItem(
                        "Regular news",
                        "24 May 2024 04 AM",
                        "",
                        "2",
                        "https://live-production.wcms.abc-cdn.net.au/29ff1c371714562351ce7c9cbe0cee64?impolicy=wcms_crop_resize&amp;cropH=3333&amp;cropW=2500&amp;xPos=1250&amp;yPos=0&amp;width=862&amp;height=1149",
                        "Description",
                        "This is where we see some news, blah blah",
                        Enclosure(
                            thumbnail = "",
                            link = "https://live-production.wcms.abc-cdn.net.au/29ff1c371714562351ce7c9cbe0cee64?impolicy=wcms_crop_resize&amp;cropH=3333&amp;cropW=2500&amp;xPos=1250&amp;yPos=0&amp;width=862&amp;height=1149"
                        ),
                        arrayOf("b")
                    )
                )
            )

            return flowOf(GenericResponse.Success(newsItems))
        }

    }

    @Test
    fun testTheUILayout() {
        val fakeRepo = FakeRepo()

        val viewModel = NewsFeedViewModel(fakeRepo);
        // Start the app
        composeTestRule.setContent {
            NewsAppTheme {
                NewsFeedLayout(
                    viewModel = viewModel, paddingValues = PaddingValues(
                        all = 2.dp
                    )
                )
            }
        }

        //composeTestRule.onNodeWithText("Top News").assertIsDisplayed()
    }
}