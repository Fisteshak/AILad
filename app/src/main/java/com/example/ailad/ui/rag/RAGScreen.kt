package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.example.ailad.domain.entities.Person
import com.example.ailad.ui.MainViewModel
import java.time.LocalDateTime


@Composable
fun RAGScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val persons by viewModel.persons.collectAsStateWithLifecycle()
    var showCreateDialog by remember { mutableStateOf(false) }
    var editDialogPersonId: Int? by remember { mutableStateOf(null) }
    var deleteDialogPersonId: Int? by remember { mutableStateOf(null) }

    Column(modifier) {

        var sortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
        var showFavorites by rememberSaveable { mutableStateOf(false) }
        PersonsHeader(
            showFavorites = showFavorites,
            onAddClick = { showCreateDialog = true },
            onSortClick = { sortOrder = it },
            onShowFavoritesClick = { showFavorites = !showFavorites },
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )

        PersonsGrid(
            when (sortOrder) {
                SortOrder.NameAsc -> persons.sortedBy { it.name }
                SortOrder.NameDesc -> persons.sortedBy { it.name }.reversed()
                SortOrder.CreationDateAsc -> persons.sortedBy { it.creationDate }
                SortOrder.CreationDateDesc -> persons.sortedBy { it.creationDate }.reversed()
                SortOrder.ChangeDateAsc -> persons.sortedBy { it.changeDate }
                SortOrder.ChangeDateDesc -> persons.sortedBy { it.changeDate }.reversed()
            }.let { list ->
                if (showFavorites) list.filter { it.isFavorite } else list
            },
            onPersonFavoriteClick = { id ->

                viewModel.updatePerson(
                    persons.find { it.id == id }!!
                        .copy(isFavorite = !persons.find { it.id == id }!!.isFavorite)
                )
            },
            onPersonDeleteClick = { id -> deleteDialogPersonId = id },
            onPersonEditClick = { editDialogPersonId = it }
        )

        if (showCreateDialog) {
            NewStringDialog(
                title = stringResource(R.string.add_person),
                hint = stringResource(R.string.imagine_you_are),
                stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
                onDismissRequest = { showCreateDialog = false },
                onAddPerson = { newPersonName ->
                    val newPerson =
                        Person(0, newPersonName, LocalDateTime.now(), LocalDateTime.now(), false)
                    viewModel.insertPerson(newPerson)

                    showCreateDialog = false
                }
            )
        }

        // needed because compiler is not able to smart cast state or smth
        val editId = editDialogPersonId
        if (editId != null) {
            NewStringDialog(
                title = stringResource(R.string.edit_person),
                hint = stringResource(R.string.imagine_you_are),
                stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
                onDismissRequest = { editDialogPersonId = null },
                onAddPerson = { newPersonName ->
                    val newPerson =
                        persons.find { it.id == editId }!!
                            .copy(name = newPersonName, changeDate = LocalDateTime.now())
                    viewModel.updatePerson(newPerson)

                    editDialogPersonId = null
                }
            )
        }
        val deleteId = deleteDialogPersonId
        if (deleteId != null) {
            AlertDialog(
                onDismissRequest = {
                    deleteDialogPersonId = null
                }, // Called when the dialog is dismissed via back press or outside click
                title = { Text("Удалить?") },
                text = { },
                confirmButton = {
                    Button(onClick = {
                        viewModel.deletePerson(persons.find { it.id == deleteId }!!)
                        deleteDialogPersonId = null
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = { deleteDialogPersonId = null }) {
                        Text("No")
                    }
                },
                properties = DialogProperties(usePlatformDefaultWidth = false) // Important for full width
            )
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







