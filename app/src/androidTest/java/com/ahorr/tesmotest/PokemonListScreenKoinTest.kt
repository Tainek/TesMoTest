package com.ahorr.tesmotest

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.domain.usecase.GetPokemonListUseCase
import com.ahorr.tesmotest.presentation.PokemonListViewModel
import com.ahorr.tesmotest.ui.PokemonListScreen


import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.stopKoin
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PokemonListScreenKoinTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        stopKoin() // Ensure no Koin context leaks between tests
        val testModule = module {
            single {
                object : GetPokemonListUseCase() {
                    override fun invoke() = flowOf(
                        PagingData.from(
                            listOf(
                                PokemonResult(name = "bulbasaur", url = "url1"),
                                PokemonResult(name = "ivysaur", url = "url2")
                            )
                        )
                    )
                }
            }
            factory { PokemonListViewModel() }
        }
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun pokemonListScreen_displaysPokemonNames() {


        composeTestRule.setContent {
            TestContentWithKoin()
        }


        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ivysaur").assertIsDisplayed()
    }

    @Composable
    private fun TestContentWithKoin() {
        val viewModel: PokemonListViewModel = koinViewModel()
        val pokemons = viewModel.pokes.collectAsLazyPagingItems()
        PokemonListScreen(
            pokemons = pokemons,
            onPokemonClick = {}
        )
    }
} 