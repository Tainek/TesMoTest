package com.ahorr.tesmotest.domain.usecase

import androidx.paging.PagingData
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class GetPokemonListUseCase() : KoinComponent {

    private val repository: PokemonRepository by inject()

    open operator fun invoke(): Flow<PagingData<PokemonResult>> {
        return repository.getPokemonList()
    }
}
