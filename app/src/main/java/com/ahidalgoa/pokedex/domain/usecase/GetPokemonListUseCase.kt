package com.ahidalgoa.pokedex.domain.usecase

import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import com.ahidalgoa.pokedex.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to get a list of Pokémon for a specific generation.
 * This class follows the single responsibility principle.
 */
class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    /**
     * Executes the use case.
     * It exposes a Flow to emit the result of the operation, making it suitable for reactive UIs.
     *
     * @param generationId The ID of the generation to fetch.
     * @return A [Flow] emitting the [Result] which is either a list of [Pokemon] or an error.
     */
    operator fun invoke(generationId: Int): Flow<Result<List<Pokemon>>> = flow {
        // The repository already returns a Result, so we just emit it inside a flow.
        // A flow builder is used to allow for future enhancements, like emitting a Loading state first.
        // e.g. emit(Result.Loading)
        emit(pokemonRepository.getPokemonList(generationId))
    }
}
