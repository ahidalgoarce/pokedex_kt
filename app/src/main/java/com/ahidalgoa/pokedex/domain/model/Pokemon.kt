package com.ahidalgoa.pokedex.domain.model

/**
 * Represents a Pokémon in a list view. Contains essential data for display.
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String
)

/**
 * Represents the detailed information of a single Pokémon.
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Float,
    val weight: Float,
    val imageUrl: String?,
    val stats: List<PokemonStat>,
    val types: List<String>,
    val description: String,
    val evolutionChain: List<PokemonEvolution>,
    val cryUrl: String?
)

/**
 * Represents a single stat of a Pokémon (e.g., HP, Attack).
 */
data class PokemonStat(
    val name: String,
    val value: Int
)

/**
 * Represents a step in the evolution chain.
 */
data class PokemonEvolution(
    val id: Int,
    val name: String
)
