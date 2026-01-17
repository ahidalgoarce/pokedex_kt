package com.ahidalgoa.pokedex.ui.view.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahidalgoa.pokedex.domain.model.PokemonEvolution

@Composable
fun PokemonEvolutionChain(evolutions: List<PokemonEvolution>, color: Color) {
    if (evolutions.size <= 1) return
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Evolution Chain", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            evolutions.forEachIndexed { index, evolution ->
                EvolutionCard(evolution, color)
                if (index < evolutions.lastIndex) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Evolves to", modifier = Modifier.padding(horizontal = 8.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun EvolutionCard(evolution: PokemonEvolution, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.3f
                )
            )
        ) {
            SubcomposeAsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${evolution.id}.png",
                loading = { CircularProgressIndicator(color = color) },
                contentDescription = evolution.name,
                modifier = Modifier.size(80.dp).padding(8.dp)
            )
        }
        Text(
            text = evolution.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
