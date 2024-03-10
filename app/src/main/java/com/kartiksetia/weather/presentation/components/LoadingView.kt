package com.kartiksetia.weather.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.kartiksetia.weather.core.system.theme.RippleColorShades

@Composable
fun LoadingView() {
    Surface(color = MaterialTheme.colorScheme.background) {
        LazyColumn {
            repeat(6) {
                item {
                    LoadingRippleEffect()
                }
            }
        }
    }
}

@Composable
fun LoadingRippleEffect(
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ), label = "Loading"
    )
    val brush = Brush.linearGradient(
        colors = RippleColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    BlankItem(brush = brush)
}

@Composable
fun BlankItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(120.dp)
                .background(
                    brush = brush
                )
        )
    }
}