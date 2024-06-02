package com.example.timesdigest.ui.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType.SEARCH
import com.example.timesdigest.ui.component.ArticleItemCompact
import com.example.timesdigest.ui.search.SearchUiState.EmptyQuery
import com.example.timesdigest.ui.search.SearchUiState.Error
import com.example.timesdigest.ui.search.SearchUiState.Loading
import com.example.timesdigest.ui.search.SearchUiState.Success
import com.example.timesdigest.ui.theme.TimesDigestTheme

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    onSaveArticleClick: (Article) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(onSearchTriggered = onSearchTriggered)
        when (searchUiState) {
            is EmptyQuery -> EmptyQueryBody()
            is Success -> SearchResultBody(
                articles = searchUiState.articles,
                onSaveArticleClick = onSaveArticleClick,
            )
            is Loading -> LoadingBody()
            is Error -> Toast.makeText(
                LocalContext.current,
                searchUiState.errorResId,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun LoadingBody() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.width(48.dp)
        )
    }
}

@Composable
private fun EmptyQueryBody() {
}

@Composable
fun SearchResultBody(
    articles: List<Article>,
    onSaveArticleClick: (Article) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        itemsIndexed(items = articles) { index, article ->
            ArticleItemCompact(
                article = article,
                onSaveArticleClick = onSaveArticleClick,
                textMaxLines = 2
            )
            if (index < articles.lastIndex) HorizontalDivider(Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun SearchTextField(onSearchTriggered: (String) -> Unit) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }
    TextField(
        value = searchQuery,
        onValueChange = {
            if ("\n" !in it) searchQuery = it
        },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        searchQuery = ""
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            },
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            },
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TimesDigestTheme(useDynamicColors = true) {
        SearchScreen(
            searchUiState = Success(
                articles = listOf(
                    Article(
                        title = "explicari explicari explicari explicari explicari explicari explicari explicari explicari",
                        subtitle = "discere discere discere discere discere discere discere discere discere discere discere discere discere discere discere discere",
                        url = "https://duckduckgo.com/?q=consul",
                        imageUrl = "http://www.bing.com/search?q=honestatis",
                        isSaved = false,
                        type = SEARCH,
                        isFresh = false
                    ),
                    Article(
                        title = "explicari",
                        subtitle = "discere",
                        url = "https://duckduckgo.com/?q=consul",
                        imageUrl = "http://www.bing.com/search?q=honestatis",
                        isSaved = false,
                        type = SEARCH,
                        isFresh = false
                    )
                ),
            ),
            onSaveArticleClick = {},
            onSearchTriggered = {})
    }
}