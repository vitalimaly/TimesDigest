package com.example.timesdigest.ui.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timesdigest.R
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType.MOST_POPULAR
import com.example.timesdigest.ui.component.ArticleItemCompact
import com.example.timesdigest.ui.saved.SavedUiState.Empty
import com.example.timesdigest.ui.saved.SavedUiState.Loading
import com.example.timesdigest.ui.saved.SavedUiState.Success
import com.example.timesdigest.ui.theme.TimesDigestTheme

@Composable
fun SavedScreen(
    savedUiState: SavedUiState,
    onSaveArticleClick: (Article) -> Unit
) {
    when (savedUiState) {
        is Success -> SavedScreenList(
            articles = savedUiState.articles,
            onSaveArticleClick = onSaveArticleClick
        )
        is Empty -> SavedEmptyState()
        is Loading -> LoadingState()
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
private fun SavedEmptyState() {
    Text(text = stringResource(R.string.saved_articles_empty_state))
}

@Composable
fun SavedScreenList(
    articles: List<Article>,
    onSaveArticleClick: (Article) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items = articles) { index, article ->
            ArticleItemCompact(
                article = article,
                onSaveArticleClick = onSaveArticleClick
            )
            if (index < articles.lastIndex) HorizontalDivider(Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TimesDigestTheme(useDynamicColors = true) {
        SavedScreen(
            savedUiState = Success(
                articles = listOf(
                    Article(
                        title = "explicari explicari explicari explicari explicari explicari explicari explicari explicari",
                        subtitle = "discere discere discere discere discere discere discere discere discere discere discere discere discere discere discere discere",
                        url = "https://duckduckgo.com/?q=consul",
                        imageUrl = "http://www.bing.com/search?q=honestatis",
                        isSaved = false,
                        type = MOST_POPULAR,
                        isFresh = false
                    ),
                    Article(
                        title = "explicari",
                        subtitle = "discere",
                        url = "https://duckduckgo.com/?q=consul",
                        imageUrl = "http://www.bing.com/search?q=honestatis",
                        isSaved = false,
                        type = MOST_POPULAR,
                        isFresh = false
                    )
                ),
            ),
            onSaveArticleClick = { })
    }
}