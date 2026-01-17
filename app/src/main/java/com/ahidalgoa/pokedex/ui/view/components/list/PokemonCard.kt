package com.ahidalgoa.pokedex.ui.view.components.list

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahidalgoa.pokedex.domain.model.Pokemon

@Composable
fun PokemonCard(pokemon: Pokemon, onPokemonClick: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onPokemonClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(96.dp),
                onSuccess = { isLoading = false },
                onError = { isLoading = false }, // Stop shimmer on error too
                loading = {
                    Box(modifier = Modifier
                        .size(96.dp)
                        .shimmerBackground(MaterialTheme.shapes.medium)
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                // Shimmer placeholders for text
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .height(14.dp) // Approximate height for bodySmall
                        .fillMaxWidth(0.4f)
                        .shimmerBackground()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(18.dp) // Approximate height for titleMedium
                        .fillMaxWidth(0.8f)
                        .shimmerBackground()
                )
            } else {
                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Private helper for shimmer effect
private fun Modifier.shimmerBackground(shape: Shape = RoundedCornerShape(4.dp)): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200)
        ),
        label = "shimmerStartOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(0.6f),
                Color.LightGray.copy(0.2f),
                Color.LightGray.copy(0.6f)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = shape
    ).onGloballyPositioned {
        size = it.size
    }
}