package com.ahidalgoa.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahidalgoa.pokedex.ui.view.screen.PokemonDetailScreen
import com.ahidalgoa.pokedex.ui.view.screen.PokemonListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.PokemonList.route
    ) {
        composable(AppDestinations.PokemonList.route) {
            PokemonListScreen(navController = navController)
        }

        composable(
            route = AppDestinations.PokemonDetail.route,
            arguments = AppDestinations.PokemonDetail.arguments
        ) {
            // The ViewModel will automatically receive the arguments
            PokemonDetailScreen(navController = navController)
        }
    }
}
