package com.ahorr.tesmotest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

import com.ahorr.tesmotest.domain.usecase.GetPokemonListUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PokemonListViewModel() : ViewModel(), KoinComponent {

    private val getPokemonListUseCase: GetPokemonListUseCase by inject()

    val pokes = getPokemonListUseCase.invoke().cachedIn(viewModelScope)

} 