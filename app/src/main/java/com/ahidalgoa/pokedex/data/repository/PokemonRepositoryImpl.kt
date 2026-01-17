package com.ahidalgoa.pokedex.data.repository

import com.ahidalgoa.pokedex.data.api.PokemonApiService
import com.ahidalgoa.pokedex.data.api.toDomain
import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import com.ahidalgoa.pokedex.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [PokemonRepository] that fetches data from the PokéAPI.
 */
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonApiService
) : PokemonRepository {

    override suspend fun getPokemonList(generationId: Int): Result<List<Pokemon>> = withContext(Dispatchers.IO) {
        try {
            // Reverted to the fast implementation: fetch generation, then map.
            val generationResponse = apiService.getPokemonByGeneration(generationId)
            Result.Success(generationResponse.toDomain())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPokemonDetail(pokemonId: Int): Result<PokemonDetail> = withContext(Dispatchers.IO) {
        try {
            val pokemonIdString = pokemonId.toString()
            val detailRequest = async { apiService.getPokemonDetail(pokemonIdString) }
            val speciesRequest = async { apiService.getPokemonSpecies(pokemonIdString) }

            val detailResponse = detailRequest.await()
            val speciesResponse = speciesRequest.await()

            val evolutionChain = speciesResponse.evolutionChain?.url?.let {
                apiService.getEvolutionChain(it).chain.toDomain()
            } ?: emptyList()

            val pokemonDetail = detailResponse.toDomain(speciesResponse, evolutionChain)
            Result.Success(pokemonDetail)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
