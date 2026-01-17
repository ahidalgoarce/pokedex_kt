package com.ahidalgoa.pokedex.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Represents the navigation destinations for the app in a type-safe way.
 */
sealed class AppDestinations(val route: String) {

    object PokemonList : AppDestinations("pokemon_list")

    object PokemonDetail : AppDestinations("pokemon_detail/{pokemonId}") {
        // Defines the argument this destination expects
        val arguments = listOf(
            navArgument("pokemonId") { type = NavType.IntType }
        )

        // Helper function to build the route with the actual pokemonId
        fun createRoute(pokemonId: Int) = "pokemon_detail/$pokemonId"
    }
}
