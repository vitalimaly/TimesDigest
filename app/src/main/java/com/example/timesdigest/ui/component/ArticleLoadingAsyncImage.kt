package com.example.timesdigest.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest.Builder

@Composable
fun ArticleLoadingAsyncImage(
    imageUrl: String?,
    loadingIndicatorDp: Dp,
    placeholderColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        loading = {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(loadingIndicatorDp)
                        .align(Alignment.Center),
                    color = placeholderColor
                )
            }
        },
        error = {
            Icon(
                imageVector = Icons.Filled.BrokenImage,
                tint = placeholderColor,
                contentDescription = null
            )

        }
    )
}
