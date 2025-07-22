package com.ahorr.tesmotest.di

import com.ahorr.tesmotest.data.remote.PokemonApi
import com.ahorr.tesmotest.data.repository.PokemonRepositoryImpl
import com.ahorr.tesmotest.domain.repository.PokemonRepository
import com.ahorr.tesmotest.domain.usecase.GetPokemonListUseCase
import com.ahorr.tesmotest.domain.usecase.GetPokemonDetailUseCase
import com.ahorr.tesmotest.presentation.detailview.PokemonDetailViewModel
import com.ahorr.tesmotest.presentation.PokemonListViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { Gson() }
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single<PokemonApi> { get<Retrofit>().create(PokemonApi::class.java) }
    single<PokemonRepository> { PokemonRepositoryImpl() }
    single { GetPokemonListUseCase() }
    single { GetPokemonDetailUseCase() }
    viewModel { PokemonListViewModel() }
    viewModel { PokemonDetailViewModel(get()) }
}

val testAppModule = module {
    single { GetPokemonListUseCase() }
    single { GetPokemonDetailUseCase() }
}