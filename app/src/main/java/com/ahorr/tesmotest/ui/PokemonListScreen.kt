package com.ahorr.tesmotest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.presentation.Dimens
import com.ahorr.tesmotest.presentation.PokemonListViewModel
import com.ahorr.tesmotest.presentation.common.PokemonCardList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    pokemons: LazyPagingItems<PokemonResult>,
    onPokemonClick: (String) -> Unit
) {

    Column {
        Spacer(Modifier.size(40.dp))
        PokemonCardList(pokemons, onPokemonClick)
    }
}


