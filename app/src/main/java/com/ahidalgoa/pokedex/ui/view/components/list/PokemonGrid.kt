package com.ahidalgoa.pokedex.ui.view.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.ui.navigation.AppDestinations

@Composable
fun PokemonGrid(pokemonList: List<Pokemon>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon = pokemon, onPokemonClick = {
                navController.navigate(AppDestinations.PokemonDetail.createRoute(pokemon.id))
            })
        }
    }
}