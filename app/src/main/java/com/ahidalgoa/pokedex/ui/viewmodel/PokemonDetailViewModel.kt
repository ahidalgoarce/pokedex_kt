package com.ahidalgoa.pokedex.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahidalgoa.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.ahidalgoa.pokedex.domain.util.Result
import com.ahidalgoa.pokedex.ui.state.PokemonDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

const val MAX_POKEMON_ID = 1025 // Adjust this as new Pokémon are released

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("pokemonId")?.let {
            loadPokemonDetail(it)
        }
    }

    fun loadNextPokemon() {
        val currentId = savedStateHandle.get<Int>("pokemonId") ?: return
        if (currentId < MAX_POKEMON_ID) {
            loadPokemonDetail(currentId + 1)
        }
    }

    fun loadPreviousPokemon() {
        val currentId = savedStateHandle.get<Int>("pokemonId") ?: return
        if (currentId > 1) {
            loadPokemonDetail(currentId - 1)
        }
    }

    fun loadPokemonDetail(pokemonId: Int) {
        _uiState.value = PokemonDetailUiState.Loading
        savedStateHandle["pokemonId"] = pokemonId

        getPokemonDetailUseCase(pokemonId).onEach { result ->
            val newState = when (result) {
                is Result.Success -> PokemonDetailUiState.Success(
                    pokemonDetail = result.data,
                    showPrevious = pokemonId > 1,
                    showNext = pokemonId < MAX_POKEMON_ID
                )
                is Result.Error -> PokemonDetailUiState.Error(result.exception.message ?: "An unknown error occurred")
            }
            _uiState.value = newState
        }.launchIn(viewModelScope)
    }
}
