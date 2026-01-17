package com.ahidalgoa.pokedex.ui.view.components.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.ui.view.common.typeColors

@Composable
fun PokemonDetailContent(pokemon: PokemonDetail, navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    val mainTypeColor = pokemon.types.firstOrNull()?.let { typeColors[it.lowercase()] } ?: MaterialTheme.colorScheme.primary

    LaunchedEffect(pokemon.id) {
        isVisible = false
        isVisible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background color that extends from top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(mainTypeColor.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )

        AnimatedVisibility(visible = isVisible) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { DetailTopBar(navController, pokemon) }
                item { PokemonHeader(pokemon, mainTypeColor) }
                item { PokemonInfoSection(pokemon) }
                item { PokemonStats(stats = pokemon.stats, types = pokemon.types) }
                item { PokemonEvolutionChain(pokemon.evolutionChain, mainTypeColor) }
                item { Spacer(modifier = Modifier.height(100.dp)) } // Spacer for the floating button
            }
        }

    }
}