package com.ahorr.tesmotest.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.model.PokemonResult

class PokePagingSource(
    private val pokeApi: PokemonApi,

    ) : PagingSource<Int, PokemonResult>() {

    private var offset = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val page = params.key ?: 1
        return try {
            val pokeResponse = pokeApi.getPokemonList(offset = offset)
            offset += pokeResponse.results.size
            val data = pokeResponse.results.distinctBy { it.name }
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}