package com.flybuys.newsapp.ui.screens.feed.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flybuys.newsapp.R
import com.flybuys.newsapp.model.Enclosure
import com.flybuys.newsapp.model.NewsItem
import com.flybuys.newsapp.model.NewsItems
import com.flybuys.newsapp.ui.screens.feed.NewsFeedViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedLayout(
    modifier: Modifier = Modifier, viewModel: NewsFeedViewModel, paddingValues: PaddingValues
) {

    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val allItems by viewModel.newsItems.collectAsState()

    val state = rememberPullToRefreshState()

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            // TODO use effects instead of this
            viewModel.loadNewsItems()
            // timer to show the refresh icon because the actual loading progress will be shown by the big progress icon
            // ideally loadNewsItems have to return something to let callers know that it has finished using either await or some other way.
            // Sorry, limited time.
            delay(500)
            state.endRefresh()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .nestedScroll(state.nestedScrollConnection)
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        if (state.isRefreshing) {
            // show pull to down refresh icon if refreshing
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
            )
            return
        } else if (loading) {
            // show loading icon if we are loading news for first time
            CircularProgressIndicator(
                modifier = Modifier.width(60.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                strokeWidth = Dp(10F)
            )
        } else {
            if (error) {
                Text(
                    text = "Breaking News: Something went wrong and we are unable to fetch news for you",
                    color = Color.Red,
                    fontSize = TextUnit(12F, TextUnitType.Sp),
                    modifier = modifier
                        .wrapContentSize()
                        .padding(all = 10.dp)
                )
            } else {
                LazyList(allItems = allItems, modifier = modifier)
            }
        }
    }
}

@Composable
fun LazyList(allItems: NewsItems, modifier: Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.news_list_separation)),
        modifier = modifier,
    ) {
        itemsIndexed(allItems.items,
            key = { _: Int, item: NewsItem -> item.guid }) { idx, item ->
            run {
                if (idx == 0) {
                    TopNewsItem(modifier = modifier, newsItem = item)
                } else {
                    RegularNewsItem(modifier = modifier, newsItem = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularNewsItem(modifier: Modifier, newsItem: NewsItem) {
    ElevatedCard(
        modifier = modifier
            .height(160.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
    ) {
        Row(
            modifier = modifier
                .padding(all = 2.dp)
                .fillMaxSize(),
        ) {

            Column(
                modifier = modifier
                    .weight(0.6F)
                    .padding(start = 3.dp, end = 3.dp)
            ) {
                Text(
                    text = newsItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(12F, TextUnitType.Sp),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = newsItem.pubDate,
                    fontSize = TextUnit(10F, TextUnitType.Sp),
                    modifier = modifier
                        .padding(bottom = 5.dp)
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.Bottom),
                )
            }

            AsyncImage(
                model = newsItem.thumbnail,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.news_ic),
                modifier = modifier
                    .weight(0.4F)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TopNewsItem(modifier: Modifier, newsItem: NewsItem) {
    ElevatedCard(
        modifier = modifier
            .height(350.dp)
            .padding(all = 0.dp)
    ) {
        Column(
            modifier = modifier
                .padding(all = 1.dp)
                .fillMaxSize(),
        ) {
            AsyncImage(
                model = newsItem.enclosure.link,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.news_ic),
                modifier = modifier
                    .weight(0.65F)
                    .wrapContentWidth(Alignment.End)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = modifier
                    .weight(0.35F)
                    .padding(top = 10.dp, end = 5.dp, start = 5.dp)
            ) {
                Text(
                    text = newsItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(16F, TextUnitType.Sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Column(modifier = modifier) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Text(
                    text = newsItem.pubDate,
                    fontSize = TextUnit(10F, TextUnitType.Sp),
                    modifier = modifier.padding(bottom = 5.dp),
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewTheUiNews() {
    RegularNewsItem(
        modifier = Modifier, newsItem = NewsItem(
            "Paramount to pay 5% dividend to its employees so whateveer this is saying is it gonna work not sure but let me try anyway",
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
        )
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = {
        Text(stringResource(id = R.string.top_bar_title))
    })
}