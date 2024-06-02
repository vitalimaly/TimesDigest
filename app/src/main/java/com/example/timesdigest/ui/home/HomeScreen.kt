package com.example.timesdigest.ui.home

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.matchParent
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType.MOST_POPULAR
import com.example.timesdigest.model.ArticleType.TOP_STORY
import com.example.timesdigest.model.HomeFeed
import com.example.timesdigest.ui.component.ArticleItemCompact
import com.example.timesdigest.ui.component.ArticleLoadingAsyncImage
import com.example.timesdigest.ui.component.DottedHorizontalDivider
import com.example.timesdigest.ui.component.SaveArticleIconToggleButton
import com.example.timesdigest.ui.home.HomeUiState.Error
import com.example.timesdigest.ui.home.HomeUiState.Loading
import com.example.timesdigest.ui.home.HomeUiState.Success
import com.example.timesdigest.ui.navigation.launchCustomTab
import com.example.timesdigest.ui.theme.TimesDigestTheme

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onSaveArticleClick: (Article) -> Unit
) {
    when (homeUiState) {
        is Error -> Toast.makeText(LocalContext.current, homeUiState.errorResId, Toast.LENGTH_SHORT)
            .show()
        is Loading -> LoadingState()
        is Success -> HomeFeedGrid(feed = homeUiState.feed, onSaveArticleClick = onSaveArticleClick)
    }
}

@Composable
fun LoadingState() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.width(48.dp)
        )
    }
}

@Composable
fun HomeFeedGrid(
    feed: HomeFeed,
    onSaveArticleClick: (Article) -> Unit
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            TopStoryHeader(article = feed.mainTopStory, onSaveArticleClick = onSaveArticleClick)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider()
        }
        itemsIndexed(
            items = feed.secondaryTopStory,
            span = { _, _ -> GridItemSpan(maxLineSpan) })
        { index, article ->
            Column {
                ArticleItemCompact(
                    article = article,
                    onSaveArticleClick = onSaveArticleClick,
                    textMaxLines = 2
                )
                if (index < feed.secondaryTopStory.lastIndex)
                    DottedHorizontalDivider(Modifier.padding(top = 8.dp))
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider()
        }
        items(feed.popularArticles) {
            PopularArticlesItem(article = it, onSaveArticleClick = onSaveArticleClick)
        }
    }
}

@Composable
private fun TopStoryHeader(
    article: Article,
    onSaveArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    val customTabColor = MaterialTheme.colorScheme.background.toArgb()
    val context = LocalContext.current
    Column(modifier = modifier.clickable {
        launchCustomTab(
            context,
            Uri.parse(article.url),
            customTabColor
        )
    }) {
        Box {
            ArticleLoadingAsyncImage(
                imageUrl = article.imageUrl,
                loadingIndicatorDp = 48.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.77f)
                    .clip(RoundedCornerShape(16.dp))
            )
            SaveArticleIconToggleButton(
                checked = article.isSaved,
                onCheckedChange = { onSaveArticleClick(article) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(top = 4.dp)
        )
        Text(
            text = article.subtitle,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun PopularArticlesItem(
    article: Article,
    onSaveArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    val customTabColor = MaterialTheme.colorScheme.background.toArgb()
    val context = LocalContext.current
    val hasImage = article.imageUrl != null
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .clickable {
            launchCustomTab(
                context,
                Uri.parse(article.url),
                customTabColor
            )
        }) {
        val (image, saveButton, title, subtitle) = createRefs()
        if (hasImage) {
            ArticleLoadingAsyncImage(
                imageUrl = article.imageUrl,
                loadingIndicatorDp = 48.dp,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
        SaveArticleIconToggleButton(
            checked = article.isSaved,
            onCheckedChange = { onSaveArticleClick(article) },
            modifier = Modifier.constrainAs(saveButton) {
                if (hasImage) {
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.end)
                } else {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            }
        )
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = if (hasImage) 4 else 6,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp, end = 24.dp)
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    width = matchParent
                }
        )
        if (!hasImage) {
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .constrainAs(subtitle) {
                        top.linkTo(title.bottom)
                        width = matchParent
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TimesDigestTheme(useDynamicColors = true) {
        HomeScreen(
            Success(
                feed = HomeFeed(
                    mainTopStory = Article(
                        title = "appareat",
                        subtitle = "equidem",
                        url = "https://www.google.com/#q=enim",
                        imageUrl = "https://static01.nyt.com/images/2024/02/14/multimedia/14xp-alaskapox/14xp-alaskapox-mediumThreeByTwo440.jpg",
                        isSaved = false,
                        type = TOP_STORY,
                        isFresh = true
                    ), secondaryTopStory = listOf(
                        Article(
                            title = "explicari explicari explicari explicari explicari explicari explicari explicari explicari",
                            subtitle = "discere discere discere discere discere discere discere discere discere discere",
                            url = "https://duckduckgo.com/?q=consul",
                            imageUrl = "http://www.bing.com/search?q=honestatis",
                            isSaved = false,
                            type = TOP_STORY,
                            isFresh = false
                        ),
                        Article(
                            title = "explicari explicari explicari explicari explicari explicari explicari explicari explicari",
                            subtitle = "discere discere discere discere discere discere discere discere discere discere",
                            url = "https://duckduckgo.com/?q=consul",
                            imageUrl = null,
                            isSaved = false,
                            type = TOP_STORY,
                            isFresh = false
                        ),
                        Article(
                            title = "explicari",
                            subtitle = "discere",
                            url = "https://duckduckgo.com/?q=consul",
                            imageUrl = "http://www.bing.com/search?q=honestatis",
                            isSaved = false,
                            type = TOP_STORY,
                            isFresh = false
                        )
                    ), popularArticles = listOf(
                        Article(
                            title = "varius",
                            subtitle = "duo",
                            url = "https://www.google.com/#q=litora",
                            imageUrl = "https://duckduckgo.com/?q=ancillae",
                            isSaved = false,
                            type = MOST_POPULAR,
                            isFresh = false
                        ),
                        Article(
                            title = "lorem ipsum dolor lorem ipsum dolor ",
                            subtitle = "duo muo pala dala bolo pako rolo mako duo muo pala dala bolo pako",
                            url = "https://www.google.com/#q=litora",
                            imageUrl = null,
                            isSaved = false,
                            type = MOST_POPULAR,
                            isFresh = false
                        ),
                    )
                )
            ),
            onSaveArticleClick = {}
        )
    }
}