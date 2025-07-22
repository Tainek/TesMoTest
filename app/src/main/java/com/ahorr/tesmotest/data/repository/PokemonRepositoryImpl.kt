package com.ahorr.tesmotest.data.repository

import androidx.compose.foundation.layout.PaddingValues
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.model.PokeDetail
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.data.remote.PokePagingSource
import com.ahorr.tesmotest.data.remote.PokemonApi
import com.ahorr.tesmotest.domain.repository.PokemonRepository
import com.ahorr.tesmotest.util.Const
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PokemonRepositoryImpl() : PokemonRepository, KoinComponent {

    private val api: PokemonApi by inject()

    override fun getPokemonList(): Flow<PagingData<PokemonResult>> {
        return Pager(
            config = PagingConfig(pageSize = Const.PAGING_SIZE),
            pagingSourceFactory = {
                PokePagingSource(api)
            }
        ).flow
    }

    override suspend fun getPokemonDetail(name: String): PokeDetail = api.getPokemonDetail(name)
} 