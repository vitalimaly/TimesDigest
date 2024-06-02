package com.example.timesdigest.ui.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.example.timesdigest.model.Article
import com.example.timesdigest.ui.navigation.launchCustomTab

@Composable
fun ArticleItemCompact(
    article: Article,
    onSaveArticleClick: (Article) -> Unit,
    textMaxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier
) {
    val customTabColor = MaterialTheme.colorScheme.background.toArgb()
    val context = LocalContext.current
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable {
            launchCustomTab(
                context,
                Uri.parse(article.url),
                customTabColor
            )
        }) {
        if (article.imageUrl != null) {
            ArticleLoadingAsyncImage(
                imageUrl = article.imageUrl,
                loadingIndicatorDp = 48.dp,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column(
            modifier = modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = textMaxLines,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = textMaxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        SaveArticleIconToggleButton(
            checked = article.isSaved,
            onCheckedChange = { onSaveArticleClick(article) },
            modifier = Modifier
                .align(Alignment.Top)
        )
    }
}
