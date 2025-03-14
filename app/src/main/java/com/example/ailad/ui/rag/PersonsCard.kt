package com.example.ailad.ui.rag

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.entities.Person


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
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
            )

            Image( // Edit icon as clickable image
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .padding(start = 12.dp) // Add some padding around the icon
                    .clickable { onEditClick() } // Make the image clickable
            )

            Image( // Delete icon as clickable image
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .padding(horizontal = 12.dp) // Add some padding around the icon
                    .clickable { onDeleteClick() } // Make the image clickable
            )

            Image( // Favorite icon (keep this)
                painter = painterResource(
                    id = if (person.isFavorite) R.drawable.star_filled_yellow else R.drawable.star_outlined_yellow
                ),
                contentDescription = "is favorite",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable { onFavoriteClick() }
            )
        }
    }
}





