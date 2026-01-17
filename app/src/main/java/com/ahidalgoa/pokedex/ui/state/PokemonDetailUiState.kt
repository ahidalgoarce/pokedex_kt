package com.ahidalgoa.pokedex.ui.state

import com.ahidalgoa.pokedex.domain.model.PokemonDetail

/**
 * Represents the different states for the Pokémon Detail screen.
 */
sealed class PokemonDetailUiState {
    /**
     * The screen is currently loading data.
     */
    object Loading : PokemonDetailUiState()

    /**
     * The data has been successfully loaded.
     * @param pokemonDetail The detailed information of the Pokémon to display.
     * @param showPrevious Whether the 'previous' navigation button should be shown.
     * @param showNext Whether the 'next' navigation button should be shown.
     */
    data class Success(
        val pokemonDetail: PokemonDetail,
        val showPrevious: Boolean,
        val showNext: Boolean
    ) : PokemonDetailUiState()

    /**
     * An error occurred while loading data.
     * @param message The error message to display.
     */
    data class Error(val message: String) : PokemonDetailUiState()
}
