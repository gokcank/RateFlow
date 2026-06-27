package com.example.rateflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.rateflow.ui.theme.GlassBorderDark
import com.example.rateflow.ui.theme.GlassBorderLight
import com.example.rateflow.ui.theme.GlassSurfaceDark
import com.example.rateflow.ui.theme.GlassSurfaceLight

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable BoxScope.() -> Unit
) {
    val surfaceColor = if (isDarkTheme) GlassSurfaceDark else GlassSurfaceLight
    val borderColor = if (isDarkTheme) GlassBorderDark else GlassBorderLight

    Box(
        modifier = modifier
            .clip(shape)
            .background(surfaceColor)
            .border(width = 1.dp, color = borderColor, shape = shape),
        content = content
    )
}
