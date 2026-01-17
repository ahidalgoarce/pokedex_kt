package com.ahidalgoa.pokedex.ui.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahidalgoa.pokedex.ui.state.PokemonListUiState
import com.ahidalgoa.pokedex.ui.view.common.ErrorView
import com.ahidalgoa.pokedex.ui.view.common.LoadingView
import com.ahidalgoa.pokedex.ui.view.components.list.GenerationSelector
import com.ahidalgoa.pokedex.ui.view.components.list.PokemonGrid
import com.ahidalgoa.pokedex.ui.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedGeneration by rememberSaveable { mutableStateOf(1) }

    Scaffold {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GenerationSelector(
                selectedGeneration = selectedGeneration,
                onGenerationSelected = { newGeneration ->
                    selectedGeneration = newGeneration
                    viewModel.loadPokemonByGeneration(newGeneration)
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = uiState) {
                    is PokemonListUiState.Loading -> LoadingView()
                    is PokemonListUiState.Success -> PokemonGrid(
                        pokemonList = state.pokemon,
                        navController = navController
                    )
                    is PokemonListUiState.Error -> ErrorView(message = state.message)
                }
            }
        }
    }
}






