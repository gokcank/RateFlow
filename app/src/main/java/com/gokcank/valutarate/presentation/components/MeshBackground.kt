package com.gokcank.valutarate.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.gokcank.valutarate.ui.theme.*

import com.gokcank.valutarate.data.preferences.ThemePalette

@Composable
fun MeshBackground(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    themePalette: ThemePalette = ThemePalette.PURPLE
) {
    val (color1, color2, color3, color4) = when (themePalette) {
        ThemePalette.PURPLE -> if (isDarkTheme) listOf(MeshColor1, MeshColor2, MeshColor3, MeshColor4) else listOf(LightMeshColor1, LightMeshColor2, LightMeshColor3, LightMeshColor4)
        ThemePalette.OCEAN -> if (isDarkTheme) listOf(OceanDark1, OceanDark2, OceanDark3, OceanDark4) else listOf(OceanLight1, OceanLight2, OceanLight3, OceanLight4)
        ThemePalette.FOREST -> if (isDarkTheme) listOf(ForestDark1, ForestDark2, ForestDark3, ForestDark4) else listOf(ForestLight1, ForestLight2, ForestLight3, ForestLight4)
        ThemePalette.SUNSET -> if (isDarkTheme) listOf(SunsetDark1, SunsetDark2, SunsetDark3, SunsetDark4) else listOf(SunsetLight1, SunsetLight2, SunsetLight3, SunsetLight4)
        ThemePalette.VICE_CITY -> if (isDarkTheme) listOf(ViceCityDark1, ViceCityDark2, ViceCityDark3, ViceCityDark4) else listOf(ViceCityLight1, ViceCityLight2, ViceCityLight3, ViceCityLight4)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(color1, color3),
                center = Offset(0f, 0f),
                radius = width * 1.5f
            )
        )

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(color2.copy(alpha = 0.6f), androidx.compose.ui.graphics.Color.Transparent),
                center = Offset(width, height * 0.3f),
                radius = width * 1.2f
            )
        )

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(color4.copy(alpha = 0.5f), androidx.compose.ui.graphics.Color.Transparent),
                center = Offset(width * 0.2f, height),
                radius = width * 1.0f
            )
        )
    }
}
