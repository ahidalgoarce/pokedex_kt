package com.ahidalgoa.pokedex.ui.state

import com.ahidalgoa.pokedex.domain.model.Pokemon

/**
 * Represents the different states for the Pokémon List screen.
 */
sealed class PokemonListUiState {
    /**
     * The screen is currently loading data.
     */
    object Loading : PokemonListUiState()

    /**
     * The data has been successfully loaded.
     * @param pokemon The list of Pokémon to display.
     */
    data class Success(val pokemon: List<Pokemon>) : PokemonListUiState()

    /**
     * An error occurred while loading data.
     * @param message The error message to display.
     */
    data class Error(val message: String) : PokemonListUiState()
}
