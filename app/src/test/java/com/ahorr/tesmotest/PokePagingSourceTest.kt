package com.ahorr.tesmotest

import androidx.paging.PagingSource
import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.data.remote.PokePagingSource
import com.ahorr.tesmotest.data.remote.PokemonApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokePagingSourceTest {
    private lateinit var pokemonApi: PokemonApi
    private lateinit var pagingSource: PokePagingSource

    @Before
    fun setUp() {
        pokemonApi = mockk()
        pagingSource = PokePagingSource(pokemonApi)
    }

    @Test
    fun `load returns Page when api returns data`() = runBlocking {
        val results = listOf(
            PokemonResult(name = "bulbasaur", url = "url1"),
            PokemonResult(name = "ivysaur", url = "url2")
        )
        val response = PokemonListResponse(results = results)
        coEvery { pokemonApi.getPokemonList(any(), any()) } returns response

        val params = PagingSource.LoadParams.Refresh<Int>(key = 1, loadSize = 2, placeholdersEnabled = false)
        val result = pagingSource.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(results, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @Test
    fun `load returns Error when api throws`() = runBlocking {
        coEvery { pokemonApi.getPokemonList(any(), any()) } throws Exception("Network error")
        val params = PagingSource.LoadParams.Refresh<Int>(key = 1, loadSize = 2, placeholdersEnabled = false)
        val result = pagingSource.load(params)
        assertTrue(result is PagingSource.LoadResult.Error)
    }
} 