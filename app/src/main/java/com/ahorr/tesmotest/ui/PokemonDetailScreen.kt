package com.ahorr.tesmotest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahorr.tesmotest.presentation.detailview.PokemonDetailViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.ahorr.tesmotest.presentation.detailview.PokeDetailCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    name: String,
    viewModel: PokemonDetailViewModel,
    onBack: () -> Unit
) {
    val detail by viewModel.pokemonDetail.collectAsState()
    
    if (detail == null || detail?.name != name) {
        viewModel.fetchPokemonDetail(name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name.replaceFirstChar { it.uppercase() }) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (detail == null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                PokeDetailCard(detail)
            }
        }
    }
} 