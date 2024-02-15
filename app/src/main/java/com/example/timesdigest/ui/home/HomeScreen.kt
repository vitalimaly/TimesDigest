package com.example.timesdigest.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.example.timesdigest.ui.common.DottedHorizontalDivider


private const val topStoryCount = 3

// -first: top story, largest size, full width
//- next: 3 top stories, small size, vertical, small image on the left + title + subtitle
//- next: popular, 10 articles, medium size, vertical, square images, 2 in a row + title
@Composable
fun HomeScreen() {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            TopStoryHeader()
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider()
        }
        // todo replace count with limit in viewmodel or else
        items(count = topStoryCount, span = { GridItemSpan(maxLineSpan) }) { index ->
            Column {
                TopStoryItem()
                if (index < topStoryCount - 1) DottedHorizontalDivider(
                    modifier = Modifier.padding(
                        top = 8.dp
                    )
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider()
        }
        // todo replace count with limit in viewmodel or else
        items(10) {
            val dp = LocalConfiguration.current.screenWidthDp.dp
            PopularArticlesItem()
        }
    }
}

@Composable
private fun TopStoryHeader(
    modifier: Modifier = Modifier
) {
    Column {
        AsyncImage(
            model = Builder(context = LocalContext.current)
                .data("https://placekitten.com/640/360")
                .crossfade(true)
                .build(),
            error = rememberVectorPainter(Icons.Filled.BrokenImage),
            placeholder = rememberVectorPainter(Icons.Filled.Autorenew),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1.77f)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = "Title with 2 lines of text that should end with three that should end with three dots so let's see how it eds!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(top = 4.dp)
        )
        Text(
            text = "Title with 2 lines of text that should end with three dots so let's see how it eds!",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun TopStoryItem(
    modifier: Modifier = Modifier
) {
    Row {
        AsyncImage(
            model = Builder(context = LocalContext.current)
                .data("https://placekitten.com/300/300")
                .crossfade(true)
                .build(),
            error = rememberVectorPainter(Icons.Filled.BrokenImage),
            placeholder = rememberVectorPainter(Icons.Filled.Autorenew),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(vertical = 2.dp)
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 8.dp)
        ) {
            Text(
                text = "Title of the article Title of the article Title of the article Title of the article Title of the article ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Subtitle of the article that can exceed 1 or 2 lines so it should end with three dots three dots three dots three dots three dots",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun PopularArticlesItem(
    modifier: Modifier = Modifier
) {
    Column {
        AsyncImage(
            model = Builder(context = LocalContext.current)
                .data("https://placekitten.com/500/500")
                .crossfade(true)
                .build(),
            error = rememberVectorPainter(Icons.Filled.BrokenImage),
            placeholder = rememberVectorPainter(Icons.Filled.Autorenew),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = "Title of the article",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {

}