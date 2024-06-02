package com.example.timesdigest.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DottedHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 2.0.dp,
    color: Color = DividerDefaults.color,
) = Canvas(
    modifier
        .fillMaxWidth()
        .height(thickness)
) {
    drawLine(
        color = color,
        strokeWidth = thickness.toPx(),
        start = Offset(0f, thickness.toPx() / 2),
        end = Offset(size.width, thickness.toPx() / 2),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(
                thickness.toPx() / 2,
                thickness.toPx() * 4
            ), 0f
        )
    )
}
