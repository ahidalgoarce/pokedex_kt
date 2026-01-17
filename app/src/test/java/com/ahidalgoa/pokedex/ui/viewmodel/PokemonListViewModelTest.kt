package com.ahidalgoa.pokedex.ui.viewmodel

import app.cash.turbine.test
import com.ahidalgoa.pokedex.data.repository.FakePokemonRepository
import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.usecase.GetPokemonListUseCase
import com.ahidalgoa.pokedex.rules.MainDispatcherRule
import com.ahidalgoa.pokedex.ui.state.PokemonListUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonListViewModel
    private lateinit var fakeRepository: FakePokemonRepository

    @Before
    fun setUp() {
        fakeRepository = FakePokemonRepository()
    }

    private fun createViewModel(): PokemonListViewModel {
        return PokemonListViewModel(GetPokemonListUseCase(fakeRepository))
    }

    @Test
    fun `when ViewModel is created, it loads generation 1 and state is Success`() = runTest {
        val pokemonList = listOf(Pokemon(1, "Bulbasaur", "url"))
        fakeRepository.setPokemonList(pokemonList)

        viewModel = createViewModel()

        // The ViewModel starts in Loading state
        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)

        // Advance the dispatcher to execute the coroutine
        runCurrent()

        // Now the state should be Success
        val successState = viewModel.uiState.value
        assertTrue(successState is PokemonListUiState.Success)
        assertEquals(pokemonList, (successState as PokemonListUiState.Success).pokemon)
    }

    @Test
    fun `loadPokemonByGeneration success`() = runTest {
        val initialList = listOf(Pokemon(1, "Bulbasaur", "url"))
        fakeRepository.setPokemonList(initialList)
        viewModel = createViewModel()
        runCurrent() // Complete the initial load

        val newList = listOf(Pokemon(152, "Chikorita", "url"))
        fakeRepository.setPokemonList(newList)

        viewModel.loadPokemonByGeneration(2)

        // The state should immediately be Loading
        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)

        // Advance the dispatcher to execute the new request
        runCurrent()

        val successState = viewModel.uiState.value
        assertTrue(successState is PokemonListUiState.Success)
        assertEquals(newList, (successState as PokemonListUiState.Success).pokemon)
    }

    @Test
    fun `when initial load fails, state is Error`() = runTest {
        fakeRepository.setShouldReturnError(true)
        viewModel = createViewModel()

        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)

        runCurrent()

        val errorState = viewModel.uiState.value
        assertTrue(errorState is PokemonListUiState.Error)
        assertEquals("Network error", (errorState as PokemonListUiState.Error).message)
    }
}
