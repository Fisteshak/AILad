package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T>PersonsGrid(
    persons: List<T>,
    cellContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    rows: Int = 3,
) {


    LazyHorizontalGrid(
        rows = GridCells.Fixed(rows),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.height(140.dp)
    ) {
        items(persons.size) { index ->
            cellContent(persons[index])

        }
    }
}


