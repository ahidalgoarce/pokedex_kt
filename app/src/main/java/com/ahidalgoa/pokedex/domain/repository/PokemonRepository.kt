package com.ahidalgoa.pokedex.domain.repository

import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.util.Result

/**
 * Interface for the Pokémon data repository.
 * Defines the contract for data operations related to Pokémon.
 */
interface PokemonRepository {

    /**
     * Fetches a list of Pokémon from a specific generation.
     * @param generationId The ID of the generation to fetch.
     * @return A [Result] wrapper containing either a list of [Pokemon] or an error.
     */
    suspend fun getPokemonList(generationId: Int): Result<List<Pokemon>>

    /**
     * Fetches the detailed information for a specific Pokémon.
     * @param pokemonId The ID of the Pokémon to fetch.
     * @return A [Result] wrapper containing either the [PokemonDetail] or an error.
     */
    suspend fun getPokemonDetail(pokemonId: Int): Result<PokemonDetail>
}
