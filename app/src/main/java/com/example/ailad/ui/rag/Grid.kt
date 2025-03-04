package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
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


    LazyColumn(
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(persons.size) { index ->
            cellContent(persons[index])

        }
    }
}


