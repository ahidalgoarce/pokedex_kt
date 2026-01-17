package com.ahidalgoa.pokedex.domain.usecase

import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import com.ahidalgoa.pokedex.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to get the detailed information for a single Pokémon.
 * This class follows the single responsibility principle.
 */
class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    /**
     * Executes the use case.
     *
     * @param pokemonId The ID of the Pokémon to fetch.
     * @return A [Flow] emitting the [Result] which is either a [PokemonDetail] object or an error.
     */
    operator fun invoke(pokemonId: Int): Flow<Result<PokemonDetail>> = flow {
        emit(pokemonRepository.getPokemonDetail(pokemonId))
    }
}
