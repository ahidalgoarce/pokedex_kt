package com.ahidalgoa.pokedex.data.api

import com.ahidalgoa.pokedex.data.api.dto.EvolutionChainResponse
import com.ahidalgoa.pokedex.data.api.dto.GenerationResponse
import com.ahidalgoa.pokedex.data.api.dto.PokemonDetailResponse
import com.ahidalgoa.pokedex.data.api.dto.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonApiService {

    /**
     * Fetches a list of Pokémon species for a given generation.
     * @param id The generation ID (e.g., 1 for Kanto).
     */
    @GET("generation/{id}")
    suspend fun getPokemonByGeneration(@Path("id") id: Int): GenerationResponse

    /**
     * Fetches detailed information for a specific Pokémon.
     * @param id The Pokémon ID or name.
     */
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: String): PokemonDetailResponse

    /**
     * Fetches species-specific information, including description and evolution chain URL.
     * @param id The Pokémon ID or name.
     */
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): PokemonSpeciesResponse

    /**
     * Fetches the evolution chain from a dynamic URL.
     * @param url The full URL provided by the species endpoint.
     */
    @GET
    suspend fun getEvolutionChain(@Url url: String): EvolutionChainResponse
}
