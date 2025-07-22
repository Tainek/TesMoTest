package com.ahorr.tesmotest.domain.usecase

import com.ahorr.tesmotest.domain.repository.PokemonRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPokemonDetailUseCase() : KoinComponent {

    private val repository: PokemonRepository by inject()

    suspend operator fun invoke(name: String) = repository.getPokemonDetail(name)
}