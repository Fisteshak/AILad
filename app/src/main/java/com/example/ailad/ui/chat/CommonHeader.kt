package com.example.ailad.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.ui.rag.SortOrder


@Composable
fun CommonHeader(
    headerText: String,
    showFavorites: Boolean,
    onShowFavoritesClick: () -> Unit, // Callback for showing favorites
    onSortClick: (SortOrder) -> Unit, // Callback for sorting, takes SortOrder
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) } // State for dropdown menu

    Row(
        modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            headerText,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(Modifier.weight(1f))

        IconButton(onClick = onShowFavoritesClick) { // Favorites icon
            Icon(
                painter = if (showFavorites) painterResource(R.drawable.star_filled_black) else painterResource(
                    R.drawable.star_outlined_black
                ),
                contentDescription = stringResource(R.string.show_favorites)
            )
        }

        Box { // Dropdown menu
            IconButton(onClick = { expanded = true }) {
                Icon(painter = painterResource(id = R.drawable.sort), contentDescription = "Sort")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.NameAsc)
                    expanded = false
                },
                    text = { Text(stringResource(R.string.name_a_z)) }
                )

                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.NameDesc)
                    expanded = false
                },
                    text = { Text(stringResource(R.string.name_z_a)) }
                )

                // Add more sort options as needed (e.g., by date)
                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.CreationDateAsc)
                    expanded = false
                },
                    text = { Text(stringResource(R.string.creation_date_oldest_first)) }
                )

                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.CreationDateDesc)
                    expanded = false
                },
                    text = { Text(stringResource(R.string.creation_date_newest_first)) }
                )

                // Add more sort options as needed (e.g., by date)
                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.ChangeDateAsc)
                    expanded = false
                },
                    text = { Text(stringResource(R.string.change_date_oldest_first)) }
                )

                DropdownMenuItem(
                    onClick = {
                        onSortClick(SortOrder.ChangeDateDesc)
                        expanded = false
                    },
                    text = { Text(stringResource(R.string.change_date_newest_first)) },

                    )
            }
        }

    }
}