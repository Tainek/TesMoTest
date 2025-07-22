package com.ahorr.tesmotest.data.remote

import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.model.PokeDetail
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.util.Const
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = Const.PAGING_SIZE,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{name}/")
    suspend fun getPokemonDetail(@Path("name") name: String): PokeDetail
} 