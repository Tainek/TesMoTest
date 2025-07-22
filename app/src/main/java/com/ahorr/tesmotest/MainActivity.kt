package com.ahorr.tesmotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahorr.tesmotest.presentation.PokemonListViewModel
import com.ahorr.tesmotest.presentation.detailview.PokemonDetailViewModel
import com.ahorr.tesmotest.ui.PokemonListScreen
import com.ahorr.tesmotest.ui.PokemonDetailScreen
import com.ahorr.tesmotest.ui.theme.TesMoTestTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesMoTestTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        val viewModel: PokemonListViewModel = koinViewModel()
                        val pokes = viewModel.pokes.collectAsLazyPagingItems()
                        PokemonListScreen(
                            pokes,
                            onPokemonClick = { name ->
                                navController.navigate("detail/$name")
                            }
                        )
                    }
                    composable(
                        "detail/{name}",
                        arguments = listOf(navArgument("name") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val viewModel: PokemonDetailViewModel = koinViewModel()
                        PokemonDetailScreen(
                            name = name,
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}