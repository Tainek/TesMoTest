package com.ahorr.tesmotest.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ahorr.tesmotest.data.model.PokemonResult

@Composable
fun PokemonListCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonResult,
    onClick: (String) -> Unit
) {

    val name = pokemon.url.trimEnd('/').split("/").last()

    Row(
        modifier = modifier
            .clickable { onClick(name) }
            .padding(16.dp, 2.dp)
    ) {
        Card {
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

    }

}

