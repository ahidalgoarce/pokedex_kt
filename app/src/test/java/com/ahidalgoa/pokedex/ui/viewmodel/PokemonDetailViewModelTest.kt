package com.ahidalgoa.pokedex.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.ahidalgoa.pokedex.data.repository.FakePokemonRepository
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.model.PokemonStat
import com.ahidalgoa.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.ahidalgoa.pokedex.rules.MainDispatcherRule
import com.ahidalgoa.pokedex.ui.state.PokemonDetailUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        fakeRepository = FakePokemonRepository()
        savedStateHandle = SavedStateHandle()
    }

    private fun createViewModel(): PokemonDetailViewModel {
        return PokemonDetailViewModel(GetPokemonDetailUseCase(fakeRepository), savedStateHandle)
    }

    @Test
    fun `when ViewModel is created, it loads initial pokemon and state is Success`() = runTest {
        savedStateHandle["pokemonId"] = 1
        val pokemonDetail = PokemonDetail(
            id = 1,
            name = "Bulbasaur",
            height = 0.7f,
            weight = 6.9f,
            imageUrl = "url.com",
            stats = listOf(
                PokemonStat("hp", 45)
            ),
            types = listOf("grass"),
            description = "desc1",
            evolutionChain = emptyList(),
            cryUrl = "cry_url"
        )
        fakeRepository.setPokemonDetail(pokemonDetail)

        viewModel = createViewModel()

        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)

        runCurrent()

        val successState = viewModel.uiState.value
        assertTrue(successState is PokemonDetailUiState.Success)
        assertEquals(pokemonDetail, (successState as PokemonDetailUiState.Success).pokemonDetail)
    }

    @Test
    fun `when initial load fails, state is Error`() = runTest {
        savedStateHandle["pokemonId"] = 1
        fakeRepository.setShouldReturnError(true)
        viewModel = createViewModel()

        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)

        runCurrent()

        val errorState = viewModel.uiState.value
        assertTrue(errorState is PokemonDetailUiState.Error)
    }

    @Test
    fun `loadNextPokemon success`() = runTest {
        savedStateHandle["pokemonId"] = 1
        val pokemonDetail1 = PokemonDetail(
            id = 1,
            name = "Bulbasaur",
            height = 0.7f,
            weight = 6.9f,
            imageUrl = "url.com",
            stats = listOf(
                PokemonStat("hp", 45)
            ),
            types = listOf("grass"),
            description = "desc1",
            evolutionChain = emptyList(),
            cryUrl = "cry_url"
        )
        val pokemonDetail2 = PokemonDetail(
            id = 2,
            name = "Ivysaur",
            height = 1.0f,
            weight = 13.0f,
            imageUrl = "url",
            stats = listOf(
                PokemonStat("hp", 60)
            ),
            types = listOf("grass", "poison"),
            description = "desc2",
            evolutionChain = emptyList(),
            cryUrl = "cry_url"
        )

        fakeRepository.setPokemonDetail(pokemonDetail1)
        viewModel = createViewModel()
        runCurrent() // Complete initial load

        fakeRepository.setPokemonDetail(pokemonDetail2)
        viewModel.loadNextPokemon()

        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)

        runCurrent()

        val nextState = viewModel.uiState.value
        assertTrue(nextState is PokemonDetailUiState.Success)
        assertEquals(pokemonDetail2, (nextState as PokemonDetailUiState.Success).pokemonDetail)
        assertEquals(2, savedStateHandle.get<Int>("pokemonId"))
    }

    @Test
    fun `loadPreviousPokemon success`() = runTest {
        savedStateHandle["pokemonId"] = 2
        val pokemonDetail1 = PokemonDetail(
            id = 1,
            name = "Bulbasaur",
            height = 0.7f,
            weight = 6.9f,
            imageUrl = "url.com",
            stats = listOf(
                PokemonStat("hp", 45)
            ),
            types = listOf("grass"),
            description = "desc1",
            evolutionChain = emptyList(),
            cryUrl = "cry_url"
        )
        val pokemonDetail2 = PokemonDetail(
            id = 2,
            name = "Ivysaur",
            height = 1.0f,
            weight = 13.0f,
            imageUrl = "url",
            stats = listOf(
                PokemonStat("hp", 60)
            ),
            types = listOf("grass", "poison"),
            description = "desc2",
            evolutionChain = emptyList(),
            cryUrl = "cry_url"
        )

        fakeRepository.setPokemonDetail(pokemonDetail2)
        viewModel = createViewModel()
        runCurrent() // Complete initial load

        fakeRepository.setPokemonDetail(pokemonDetail1)
        viewModel.loadPreviousPokemon()

        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)

        runCurrent()

        val prevState = viewModel.uiState.value
        assertTrue(prevState is PokemonDetailUiState.Success)
        assertEquals(pokemonDetail1, (prevState as PokemonDetailUiState.Success).pokemonDetail)
        assertEquals(1, savedStateHandle.get<Int>("pokemonId"))
    }
}
