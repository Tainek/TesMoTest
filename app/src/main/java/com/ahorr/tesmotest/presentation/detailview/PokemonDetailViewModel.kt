package com.ahorr.tesmotest.presentation.detailview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahorr.tesmotest.data.model.PokeDetail
import com.ahorr.tesmotest.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    ViewModel() {
    private val _pokemonDetail = MutableStateFlow<PokeDetail?>(null)
    val pokemonDetail: StateFlow<PokeDetail?> = _pokemonDetail

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                _pokemonDetail.value = getPokemonDetailUseCase(name)
            } catch (e: Exception) {
                _pokemonDetail.value = null
            }
        }
    }
}