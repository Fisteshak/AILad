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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.entities.Place


@Composable
fun CreatePlaceDialog(
    onDismissRequest: () -> Unit,
    onAddPerson: (String) -> Unit
) {
    NewStringDialog(
        title = stringResource(R.string.add_place),
        hint = stringResource(R.string.imagine_you_are_at),
        stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
        onDismissRequest = onDismissRequest,
        onAddPerson = onAddPerson
    )
}


@Composable
fun EditPlaceDialog(
    editDialogPlaceId: Int?,
    onDismissRequest: () -> Unit,
    onEditPerson: (Int, String) -> Unit
) {
    if (editDialogPlaceId != null) {
        NewStringDialog(
            title = stringResource(R.string.edit_place),
            hint = stringResource(R.string.imagine_you_are_at),
            stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
            onDismissRequest = onDismissRequest,
            onAddPerson = { newName -> onEditPerson(editDialogPlaceId, newName) }
        )
    }
}


@Composable
fun PlaceCard(
    place: Place,
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
                text = place.name,
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
                    id = if (place.isFavorite) R.drawable.star_filled_yellow else R.drawable.star_outlined_yellow
                ),
                contentDescription = "is favorite",
                modifier = Modifier
                    .clickable { onFavoriteClick() }
                    .padding(end = 12.dp)
            )
        }
    }
}




