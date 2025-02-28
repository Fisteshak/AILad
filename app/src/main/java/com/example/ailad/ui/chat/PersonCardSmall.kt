package com.example.ailad.ui.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place

@Composable
fun PersonCardSmall(
    person: Person,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .width(210.dp)
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier

        ) {

            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier

                    .padding(horizontal = 16.dp)
                    .clickable {
                        onClick()
                    }
                    .weight(1f),
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


@Composable
fun PlaceCardSmall(
    person: Place,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .width(210.dp)
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier

        ) {

            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier

                    .padding(horizontal = 16.dp)
                    .clickable {
                        onClick()
                    }
                    .weight(1f),
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


@Composable
fun PersonCardEmpty(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, Color.Gray), // Dashed border
        modifier = modifier
            .clickable {
                onClick()
            }
            .width(210.dp)
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Selection",
                tint = Color.Gray, // Grayed-out icon
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "No Selection",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = Color.Gray, // Grayed-out text
                modifier = Modifier.weight(1f)
            )
        }
    }
}