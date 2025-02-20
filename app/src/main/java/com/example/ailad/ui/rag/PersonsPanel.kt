package com.example.ailad.ui.rag

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.data.repositories.testData
import com.example.ailad.domain.entities.Person

@Composable
fun PersonCard(
    person: Person,
    onFavoriteClick: () -> Unit,
    onEditClick: () -> Unit, // Callback for edit
    onDeleteClick: () -> Unit, // Callback for delete
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(320.dp)
                .height(40.dp)
        ) {

            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
            )

            Image( // Edit icon as clickable image
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .clickable { onEditClick() } // Make the image clickable
                    .padding(start = 12.dp) // Add some padding around the icon
            )

            Image( // Delete icon as clickable image
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .clickable { onDeleteClick() } // Make the image clickable
                    .padding(horizontal = 12.dp) // Add some padding around the icon
            )

            Image( // Favorite icon (keep this)
                painter = painterResource(
                    id = if (person.isFavorite) R.drawable.star_filled else R.drawable.star_outlined
                ),
                contentDescription = "is favorite",
                modifier = Modifier
                    .clickable { onFavoriteClick() }
                    .padding(end = 12.dp)
            )
        }
    }
}

@Composable
@Preview
fun PersonCardPreview() {
    val person by remember { mutableStateOf(testData[3]) }
    PersonCard(person, {}, {}, {})
}

@Composable
fun PersonsGrid(
    persons: List<Person>,
    onPersonFavoriteClick: (Int) -> Unit,
    onPersonEditClick: (Int) -> Unit,
    onPersonDeleteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.height(140.dp)
    ) {
        items(persons.size) { index ->
            PersonCard(
                persons[index],
                onFavoriteClick = { onPersonFavoriteClick(persons[index].id) },
                onDeleteClick = { onPersonDeleteClick(persons[index].id) },
                onEditClick = { onPersonEditClick(persons[index].id) },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PersonsHeader(
    showFavorites: Boolean,
    onAddClick: () -> Unit,
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
            "Люди",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(Modifier.weight(1f))

        IconButton(onClick = onShowFavoritesClick) { // Favorites icon
            Icon(
                imageVector = if (showFavorites) Icons.Filled.Star else Icons.Rounded.Star,
                contentDescription = "Show Favorites"
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
                    text = { Text("Name (A-Z)") }
                )

                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.NameDesc)
                    expanded = false
                },
                    text = { Text("Name (Z-A)") }
                )

                // Add more sort options as needed (e.g., by date)
                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.CreationDateAsc)
                    expanded = false
                },
                    text = { Text("Creation Date (Oldest First)") }
                )

                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.CreationDateDesc)
                    expanded = false
                },
                    text = { Text("Creation Date (Newest First)") }
                )

                // Add more sort options as needed (e.g., by date)
                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.ChangeDateAsc)
                    expanded = false
                },
                    text = { Text("Change Date (Oldest First)") }
                )

                DropdownMenuItem(onClick = {
                    onSortClick(SortOrder.ChangeDateDesc)
                    expanded = false
                },
                    text = { Text("Change Date (Newest First)") }
                )
            }
        }

        IconButton(
            onClick = onAddClick,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }
}


enum class SortOrder {
    NameAsc, NameDesc, CreationDateAsc, CreationDateDesc, ChangeDateAsc, ChangeDateDesc,
}


