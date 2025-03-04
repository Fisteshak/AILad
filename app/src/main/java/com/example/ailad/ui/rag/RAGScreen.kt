package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.example.ailad.entities.Prompt
import com.example.ailad.ui.MainViewModel
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

fun getSortedAndFilteredPrompts(
    prompts: List<Prompt>,
    sortOrder: SortOrder,
    showFavorites: Boolean
): List<Prompt> {
    val sortedPrompts = when (sortOrder) {
        SortOrder.NameAsc -> prompts.sortedBy { it.name }
        SortOrder.NameDesc -> prompts.sortedBy { it.name }.reversed()
        SortOrder.CreationDateAsc -> prompts.sortedBy { it.creationDate }
        SortOrder.CreationDateDesc -> prompts.sortedBy { it.creationDate }.reversed()
        SortOrder.ChangeDateAsc -> prompts.sortedBy { it.changeDate }
        SortOrder.ChangeDateDesc -> prompts.sortedBy { it.changeDate }.reversed()
    }
    return if (showFavorites) sortedPrompts.filter { it.isFavorite } else sortedPrompts
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RAGScreen(
    onNavigateToChat: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel: MainViewModel = hiltViewModel()
    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val places by viewModel.places.collectAsStateWithLifecycle()
    val prompts by viewModel.prompts.collectAsStateWithLifecycle()



    Column(modifier) {


        var selectedIndex = viewModel.selectedTab.intValue
        val options = listOf(stringResource(R.string.persons), stringResource(R.string.places), stringResource(R.string.prompts))

        SingleChoiceSegmentedButtonRow(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = { viewModel.selectedTab.intValue = index },
                    selected = index == selectedIndex,
                    label = { Text(label) }
                )
            }
        }

        when (selectedIndex) {
            0 -> {
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
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                PersonsGrid(
                    persons = getSortedAndFilteredPersons(
                        persons,
                        personsSortOrder,
                        personsShowFavorites
                    ),
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
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )

                CreatePersonDialog(
                    showCreateDialog = showPersonCreateDialog,
                    onDismissRequest = { showPersonCreateDialog = false },
                    onAddPerson = { newPersonName ->
                        val newPerson =
                            Person(
                                0,
                                newPersonName,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                false
                            )
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


            }

            1 -> {
                var showPlaceCreateDialog by rememberSaveable { mutableStateOf(false) }
                var editDialogPlaceId: Int? by rememberSaveable { mutableStateOf(null) }
                var deleteDialogPlaceId: Int? by rememberSaveable { mutableStateOf(null) }

                var placesSortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
                var placesShowFavorites by rememberSaveable { mutableStateOf(false) }



                PersonsHeader(
                    headerText = stringResource(R.string.places),
                    showFavorites = placesShowFavorites,
                    onAddClick = { showPlaceCreateDialog = true },
                    onSortClick = { placesSortOrder = it },
                    onShowFavoritesClick = { placesShowFavorites = !placesShowFavorites },
                    modifier = Modifier.padding(top = 0.dp, bottom = 8.dp),
                )

                PersonsGrid(
                    persons = getSortedAndFilteredPlaces(
                        places,
                        placesSortOrder,
                        placesShowFavorites
                    ),
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
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )
                if (showPlaceCreateDialog) {

                    CreatePlaceDialog(
                        onDismissRequest = { showPlaceCreateDialog = false },
                        onAddPerson = { newPlaceName ->
                            val newPlace =
                                Place(
                                    0,
                                    newPlaceName,
                                    LocalDateTime.now(),
                                    LocalDateTime.now(),
                                    false
                                )
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

            2 -> { // Prompts section
                var showPromptCreateDialog by rememberSaveable { mutableStateOf(false) }
                var editDialogPromptId: Int? by rememberSaveable { mutableStateOf(null) }
                var deleteDialogPromptId: Int? by rememberSaveable { mutableStateOf(null) }

                var promptsSortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
                var promptsShowFavorites by rememberSaveable { mutableStateOf(false) }

                PersonsHeader(
                    headerText = "Prompts",
                    showFavorites = promptsShowFavorites,
                    onAddClick = { showPromptCreateDialog = true },
                    onSortClick = { promptsSortOrder = it },
                    onShowFavoritesClick = { promptsShowFavorites = !promptsShowFavorites },
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                PersonsGrid(
                    persons = getSortedAndFilteredPrompts(
                        prompts,
                        promptsSortOrder,
                        promptsShowFavorites
                    ),
                    cellContent = { item ->
                        val id = item.id
                        PromptCard(
                            item,
                            onFavoriteClick = {
                                viewModel.updatePrompt(
                                    prompts.find { it.id == id }!!
                                        .copy(isFavorite = !prompts.find { it.id == id }!!.isFavorite)
                                )
                            },
                            onDeleteClick = { deleteDialogPromptId = id },
                            onEditClick = { editDialogPromptId = id },
                            onRunClick = { onNavigateToChat(it, true) },
                            onLoadClick = { onNavigateToChat(it, false) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )

                CreatePromptDialog(
                    showCreateDialog = showPromptCreateDialog,
                    onDismissRequest = { showPromptCreateDialog = false },
                    onAddPrompt = { newPromptName ->
                        val newPrompt =
                            Prompt(
                                0,
                                newPromptName,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                false
                            )
                        viewModel.insertPrompt(newPrompt)
                        showPromptCreateDialog = false
                    },
                )
                if (editDialogPromptId != null)
                    EditPromptDialog(
                        promptText = prompts.find { it.id == editDialogPromptId }!!.name,
                        editDialogPromptId = editDialogPromptId,
                        onDismissRequest = { editDialogPromptId = null },
                        onEditPrompt = { id, newPromptName ->
                            val updatedPrompt = prompts.find { it.id == id }!!
                                .copy(name = newPromptName, changeDate = LocalDateTime.now())
                            viewModel.updatePrompt(updatedPrompt)
                            editDialogPromptId = null
                        }
                    )

                DeleteDialog(
                    deleteDialogItemId = deleteDialogPromptId,
                    onDismissRequest = { deleteDialogPromptId = null },
                    onDeletePerson = { id ->
                        val promptToDelete = prompts.find { it.id == id }!!
                        viewModel.deletePrompt(promptToDelete)
                        deleteDialogPromptId = null
                    }
                )
            }
        }
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
                    modifier = Modifier.fillMaxWidth()
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
        properties = DialogProperties(usePlatformDefaultWidth = false), // Important for full width,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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






