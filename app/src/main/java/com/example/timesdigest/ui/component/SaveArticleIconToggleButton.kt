package com.example.timesdigest.ui.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.timesdigest.ui.theme.TimesDigestTheme

@Composable
fun SaveArticleIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) = FilledIconToggleButton(
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = IconButtonDefaults.iconToggleButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
    ),
    shape = CircleShape,
    modifier = modifier,
) {
    val icon = if (checked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder
    Icon(
        imageVector = icon,
        tint = MaterialTheme.colorScheme.tertiary,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
    TimesDigestTheme(useDynamicColors = true) {
        SaveArticleIconToggleButton(
            checked = true,
            onCheckedChange = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreviewUnchecked() {
    TimesDigestTheme(useDynamicColors = true) {
        SaveArticleIconToggleButton(
            checked = false,
            onCheckedChange = { },
        )
    }
}
