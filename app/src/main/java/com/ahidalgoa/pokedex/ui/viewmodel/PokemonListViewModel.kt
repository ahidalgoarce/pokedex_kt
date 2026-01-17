package com.ahidalgoa.pokedex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahidalgoa.pokedex.domain.usecase.GetPokemonListUseCase
import com.ahidalgoa.pokedex.domain.util.Result
import com.ahidalgoa.pokedex.ui.state.PokemonListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        // Load the first generation by default
        loadPokemonByGeneration(1)
    }

    fun loadPokemonByGeneration(generationId: Int) {
        _uiState.value = PokemonListUiState.Loading
        getPokemonListUseCase(generationId).onEach { result ->
            val newState = when (result) {
                is Result.Success -> PokemonListUiState.Success(result.data)
                is Result.Error -> PokemonListUiState.Error(result.exception.message ?: "An unknown error occurred")
            }
            _uiState.value = newState
        }.launchIn(viewModelScope)
    }
}
