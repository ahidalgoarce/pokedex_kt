package com.ahidalgoa.pokedex.ui.view.screen


import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahidalgoa.pokedex.ui.state.PokemonDetailUiState
import com.ahidalgoa.pokedex.ui.view.common.ErrorView
import com.ahidalgoa.pokedex.ui.view.common.LoadingView
import com.ahidalgoa.pokedex.ui.view.components.detail.PokemonDetailContent
import com.ahidalgoa.pokedex.ui.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) {
            padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .pointerInput(uiState) { // Gesture navigation
                    var totalDrag = 0f
                    detectHorizontalDragGestures(
                        onDragStart = { totalDrag = 0f },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            totalDrag += dragAmount
                        },
                        onDragEnd = {
                            val state = uiState
                            if (state is PokemonDetailUiState.Success) {
                                val threshold = size.width * 0.25f
                                when {
                                    totalDrag < -threshold && state.showNext -> viewModel.loadNextPokemon()
                                    totalDrag > threshold && state.showPrevious -> viewModel.loadPreviousPokemon()
                                }
                            }
                        }
                    )
                }
        ) {
            when (val state = uiState) {
                is PokemonDetailUiState.Loading -> LoadingView()
                is PokemonDetailUiState.Success -> {
                    PokemonDetailContent(pokemon = state.pokemonDetail, navController = navController)
                }
                is PokemonDetailUiState.Error -> ErrorView(message = state.message)
            }
        }
    }
}
