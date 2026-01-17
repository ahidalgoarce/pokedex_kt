package com.ahidalgoa.pokedex.ui.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val typeColors = mapOf(
    "normal" to Color(0xFFA8A77A),
    "fire" to Color(0xFFEE8130),
    "water" to Color(0xFF6390F0),
    "electric" to Color(0xFFF7D02C),
    "grass" to Color(0xFF7AC74C),
    "ice" to Color(0xFF96D9D6),
    "fighting" to Color(0xFFC22E28),
    "poison" to Color(0xFFA33EA1),
    "ground" to Color(0xFFE2BF65),
    "flying" to Color(0xFFA98FF3),
    "psychic" to Color(0xFFF95587),
    "bug" to Color(0xFFA6B91A),
    "rock" to Color(0xFFB6A136),
    "ghost" to Color(0xFF735797),
    "dragon" to Color(0xFF6F35FC),
    "dark" to Color(0xFF705746),
    "steel" to Color(0xFFB7B7CE),
    "fairy" to Color(0xFFD685AD)
)

@Composable
fun TypeChip(type: String) {
    val typeColor = typeColors[type.lowercase()] ?: Color.Gray
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(typeColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = type,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(24.dp)
                    .size(56.dp)
            )
        }
    }
}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}
