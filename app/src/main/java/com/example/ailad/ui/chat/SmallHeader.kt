package com.example.ailad.ui.chat

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place

@Composable
fun SmallHeader(
    headerText: String,
    person: Person?,
    onAddClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            headerText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 10.dp).weight(1f)
        )

        IconButton(
            onClick = onAddClick,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
        if (person != null) {
            PersonCardSmall(person, onFavoriteClick = { onFavoriteClick() }, onClick = {})
        } else {
            PersonCardEmpty({})
        }

    }
}

@Composable
fun SmallHeaderPlace(
    headerText: String,
    person: Place?,
    onAddClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            headerText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 10.dp).weight(1f)
        )

        IconButton(
            onClick = onAddClick,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }

        if (person != null) {
            PlaceCardSmall(person, onFavoriteClick = { onFavoriteClick() }, onClick = {})
        } else {
            PersonCardEmpty({})
        }

    }
}