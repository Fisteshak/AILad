package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.R
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place
import java.time.LocalDateTime

fun getSortedAndFilteredPersons(
    persons: List<Person>,
    sortOrder: SortOrder,
    showFavorites: Boolean
): List<Person> {
    val sortedPersons = when (sortOrder) {
        SortOrder.NameAsc -> persons.sortedBy { it.name }
        SortOrder.NameDesc -> persons.sortedBy { it.name }.reversed()
        SortOrder.CreationDateAsc -> persons.sortedBy { it.creationDate }
        SortOrder.CreationDateDesc -> persons.sortedBy { it.creationDate }.reversed()
        SortOrder.ChangeDateAsc -> persons.sortedBy { it.changeDate }
        SortOrder.ChangeDateDesc -> persons.sortedBy { it.changeDate }.reversed()
    }
    return if (showFavorites) sortedPersons.filter { it.isFavorite } else sortedPersons
}


fun getSortedAndFilteredPlaces(
    places: List<Place>,
    sortOrder: SortOrder,
    showFavorites: Boolean
): List<Place> {
    val sorted = when (sortOrder) {
        SortOrder.NameAsc -> places.sortedBy { it.name }
        SortOrder.NameDesc -> places.sortedBy { it.name }.reversed()
        SortOrder.CreationDateAsc -> places.sortedBy { it.creationDate }
        SortOrder.CreationDateDesc -> places.sortedBy { it.creationDate }.reversed()
        SortOrder.ChangeDateAsc -> places.sortedBy { it.changeDate }
        SortOrder.ChangeDateDesc -> places.sortedBy { it.changeDate }.reversed()
    }
    return if (showFavorites) sorted.filter { it.isFavorite } else sorted
}

@Composable
fun RAGScreen(modifier: Modifier = Modifier) {

    val viewModel: RAGViewModel = hiltViewModel()
    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val places by viewModel.places.collectAsStateWithLifecycle()



    Column(modifier) {

        var showPersonCreateDialog by rememberSaveable { mutableStateOf(false) }
        var editDialogPersonId: Int? by rememberSaveable { mutableStateOf(null) }
        var deleteDialogPersonId: Int? by rememberSaveable { mutableStateOf(null) }

        var personsSortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
        var personsShowFavorites by rememberSaveable { mutableStateOf(false) }

        PersonsHeader(
            headerText = stringResource(R.string.persons),
            showFavorites = personsShowFavorites,
            onAddClick = { showPersonCreateDialog = true },
            onSortClick = { personsSortOrder = it },
            onShowFavoritesClick = { personsShowFavorites = !personsShowFavorites },
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )

        PersonsGrid(
            persons = getSortedAndFilteredPersons(persons, personsSortOrder, personsShowFavorites),
            cellContent = { item ->
                val id = item.id
                PersonCard(
                    item,
                    onFavoriteClick = {
                        viewModel.updatePerson(
                            persons.find { it.id == id }!!
                                .copy(isFavorite = !persons.find { it.id == id }!!.isFavorite)
                        )
                    },
                    onDeleteClick = { deleteDialogPersonId = id },
                    onEditClick = { editDialogPersonId = id },
                    modifier = Modifier
                )
            },
        )

        CreatePersonDialog(
            showCreateDialog = showPersonCreateDialog,
            onDismissRequest = { showPersonCreateDialog = false },
            onAddPerson = { newPersonName ->
                val newPerson =
                    Person(0, newPersonName, LocalDateTime.now(), LocalDateTime.now(), false)
                viewModel.insertPerson(newPerson)
                showPersonCreateDialog = false
            },
        )

        EditPersonDialog(
            editDialogPersonId = editDialogPersonId,
            onDismissRequest = { editDialogPersonId = null },
            onEditPerson = { id, newPersonName ->
                val updatedPerson = persons.find { it.id == id }!!
                    .copy(name = newPersonName, changeDate = LocalDateTime.now())
                viewModel.updatePerson(updatedPerson)
                editDialogPersonId = null
            }
        )

        DeleteDialog(
            deleteDialogItemId = deleteDialogPersonId,
            onDismissRequest = { deleteDialogPersonId = null },
            onDeletePerson = { id ->
                val personToDelete = persons.find { it.id == id }!!
                viewModel.deletePerson(personToDelete)
                deleteDialogPersonId = null
            }
        )

        var showPlaceCreateDialog by rememberSaveable { mutableStateOf(false) }
        var editDialogPlaceId: Int? by rememberSaveable { mutableStateOf(null) }
        var deleteDialogPlaceId: Int? by rememberSaveable { mutableStateOf(null) }

        var placesSortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
        var placesShowFavorites by rememberSaveable { mutableStateOf(false) }

        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        PersonsHeader(
            headerText = stringResource(R.string.places),
            showFavorites = placesShowFavorites,
            onAddClick = { showPlaceCreateDialog = true },
            onSortClick = { placesSortOrder = it },
            onShowFavoritesClick = { placesShowFavorites = !placesShowFavorites },
            modifier = Modifier.padding(top = 0.dp, bottom = 8.dp),
        )

        PersonsGrid(
            persons = getSortedAndFilteredPlaces(places, placesSortOrder, placesShowFavorites),
            cellContent = { item ->
                val id = item.id
                PlaceCard(
                    item,
                    onFavoriteClick = {
                        viewModel.updatePlace(
                            places.find { it.id == id }!!
                                .copy(isFavorite = !places.find { it.id == id }!!.isFavorite)
                        )
                    },
                    onDeleteClick = { deleteDialogPlaceId = id },
                    onEditClick = { editDialogPlaceId = id },
                    modifier = Modifier
                )
            },
        )
        if (showPlaceCreateDialog) {

            CreatePlaceDialog(
                onDismissRequest = { showPlaceCreateDialog = false },
                onAddPerson = { newPlaceName ->
                    val newPlace =
                        Place(0, newPlaceName, LocalDateTime.now(), LocalDateTime.now(), false)
                    viewModel.insertPlace(newPlace)
                    showPlaceCreateDialog = false
                },
            )
        }

        EditPlaceDialog(
            editDialogPlaceId = editDialogPlaceId,
            onDismissRequest = { editDialogPlaceId = null },
            onEditPerson = { id, newPlaceName ->
                val updatedPlace = places.find { it.id == id }!!
                    .copy(name = newPlaceName, changeDate = LocalDateTime.now())
                viewModel.updatePlace(updatedPlace)
                editDialogPlaceId = null
            }
        )

        DeleteDialog(
            deleteDialogItemId = deleteDialogPlaceId,
            onDismissRequest = { deleteDialogPlaceId = null },
            onDeletePerson = { id ->
                val placeToDelete = places.find { it.id == id }!!
                viewModel.deletePlace(placeToDelete)
                deleteDialogPlaceId = null
            }
        )


    }
}

@Composable
fun NewStringDialog(
    title: String,
    text: String = "",
    hint: String = "",
    stringCantBeEmptyHint: String = "",
    onDismissRequest: () -> Unit,
    onAddPerson: (String) -> Unit
) {
    var newPersonName by remember { mutableStateOf(text) }
    var showHint by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = {
            Column {

                OutlinedTextField(
                    value = newPersonName,
                    onValueChange = { newPersonName = it },
                    label = { Text(hint) },
                )
                if (showHint)
                    Text(
                        stringCantBeEmptyHint,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.height(20.dp)
                    )

            }
        },
        confirmButton = {
            Button(onClick = {
                if (newPersonName.isNotEmpty())
                    onAddPerson(newPersonName)
                else showHint = true
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false) // Important for full width
    )
}

@Composable
fun DeleteDialog(
    deleteDialogItemId: Int?,
    onDismissRequest: () -> Unit,
    onDeletePerson: (Int) -> Unit
) {
    if (deleteDialogItemId != null) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(stringResource(R.string.delete)) },
            text = { },
            confirmButton = {
                Button(onClick = { onDeletePerson(deleteDialogItemId) }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = onDismissRequest) {
                    Text(stringResource(R.string.no))
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        )
    }
}






