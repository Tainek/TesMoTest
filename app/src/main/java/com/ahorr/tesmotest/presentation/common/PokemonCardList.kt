package com.ahorr.tesmotest.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ahorr.tesmotest.data.model.PokemonResult
import com.ahorr.tesmotest.presentation.Dimens

@Composable
fun PokemonCardList(
    pokies: LazyPagingItems<PokemonResult>,
    onClick: (String) -> Unit
) {
    val handlePagingResult = handlePagingResult(pokies)
    if (handlePagingResult) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1)
        ) {
            items(count = pokies.itemCount) {
                pokies[it]?.let {
                    PokemonListCard(pokemon = it, onClick = { onClick(it) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(pokies: LazyPagingItems<PokemonResult>): Boolean {
    val loadState = pokies.loadState
    val error = when {
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            false
        }

        error != null -> {
            EmptyPagingScreen(error)
            false
        }

        pokies.itemCount == 0 -> {
            EmptyPagingScreen()
            return false
        }

        else -> {
            true
        }
    }

}
