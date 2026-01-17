package com.ahidalgoa.pokedex.data.repository

import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import com.ahidalgoa.pokedex.domain.util.Result

class FakePokemonRepository : PokemonRepository {

    private var pokemonList = listOf(
        Pokemon(
            name = "bulbasaur",
            id = 1,
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/1/",

            ),
        Pokemon(
            name = "ivysaur",
            id = 2,
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/2/"
        ),
        Pokemon(
            name = "venusaur",
            id = 3,
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/3/"
        ),
        Pokemon(
            id = 4,
            name = "charmander",
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/4/"
        ),
        Pokemon(
            id = 5,
            name = "charmeleon",
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/5/"
        ),
        Pokemon(
            id = 6,
            name = "charizard",
            imageUrl = "https://pokeapi.co/api/v2/pokemon-species/6/"
        )
    )
    private var pokemonDetail: PokemonDetail? = null
    private var shouldReturnError = false

    fun setPokemonList(pokemon: List<Pokemon>) {
        pokemonList = pokemon
    }

    fun setPokemonDetail(detail: PokemonDetail) {
        pokemonDetail = detail
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getPokemonList(generation: Int): Result<List<Pokemon>> {
        return if (shouldReturnError) {
            Result.Error(Exception("Network error"))
        } else {
            Result.Success(pokemonList)
        }
    }

    override suspend fun getPokemonDetail(pokemonId: Int): Result<PokemonDetail> {
        return if (shouldReturnError || pokemonDetail == null) {
            Result.Error(Exception("Network error or not found"))
        } else {
            Result.Success(pokemonDetail!!)
        }
    }
}
