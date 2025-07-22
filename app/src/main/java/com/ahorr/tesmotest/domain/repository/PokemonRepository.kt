package com.ahorr.tesmotest.domain.repository

import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.model.PokeDetail
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.ahorr.tesmotest.data.model.PokemonResult

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonResult>>
    suspend fun getPokemonDetail(name: String): PokeDetail
} 