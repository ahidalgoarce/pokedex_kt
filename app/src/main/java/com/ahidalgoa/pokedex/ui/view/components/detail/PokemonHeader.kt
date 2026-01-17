package com.ahidalgoa.pokedex.ui.view.components.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahidalgoa.pokedex.domain.model.PokemonDetail

@Composable
fun PokemonHeader(pokemon: PokemonDetail, color: Color) {
    SubcomposeAsyncImage(
        model = pokemon.imageUrl,
        loading = { CircularProgressIndicator(color = color) },
        contentDescription = pokemon.name,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(bottom = 16.dp)
    )
}