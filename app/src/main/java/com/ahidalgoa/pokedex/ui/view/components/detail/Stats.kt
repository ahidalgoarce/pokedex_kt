package com.ahidalgoa.pokedex.ui.view.components.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ahidalgoa.pokedex.domain.model.PokemonStat
import com.ahidalgoa.pokedex.ui.view.common.typeColors
import kotlin.collections.forEach

@Composable
fun PokemonStats(stats: List<PokemonStat>, types: List<String>) {
    val mainTypeColor = types.firstOrNull()?.let { typeColors[it.lowercase()] } ?: MaterialTheme.colorScheme.primary
    val totalStats = remember(stats) { stats.sumOf { it.value } }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   .Bottom) {
            Text("Base Stats", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("TOTAL: $totalStats", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = mainTypeColor)
        }
        Spacer(modifier = Modifier.height(16.dp))
        stats.forEach { stat ->
            StatRow(stat = stat, mainTypeColor = mainTypeColor)
        }
    }
}

private fun getStatAbbreviation(statName: String): String {
    return when (statName.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "Sp. ATK"
        "special-defense" -> "Sp. DEF"
        "speed" -> "SPD"
        else -> statName.uppercase()
    }
}

@Composable
fun StatRow(stat: PokemonStat, mainTypeColor: Color) {
    var isAnimated by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (isAnimated) stat.value / 255f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        isAnimated = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = getStatAbbreviation(stat.name), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, modifier = Modifier.width(60.dp))
        Text(text = stat.value.toString(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, modifier = Modifier.width(40.dp))
        // Custom progress bar with background
        Box(modifier = Modifier.height(12.dp).fillMaxWidth().clip(MaterialTheme.shapes.small)) {
            Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)))
            Box(modifier = Modifier.fillMaxWidth(animatedProgress).fillMaxSize().background(mainTypeColor))
        }
    }
}