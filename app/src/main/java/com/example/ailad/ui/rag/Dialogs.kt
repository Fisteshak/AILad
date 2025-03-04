package com.example.ailad.ui.rag

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ailad.R

@Composable
fun CreatePersonDialog(
    showCreateDialog: Boolean,
    onDismissRequest: () -> Unit,
    onAddPerson: (String) -> Unit
) {
    if (showCreateDialog) {
        NewStringDialog(
            title = stringResource(R.string.add_person),
            hint = stringResource(R.string.imagine_you_are),
            stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
            onDismissRequest = onDismissRequest,
            onAddPerson = onAddPerson
        )
    }
}


@Composable
fun EditPersonDialog(
    editDialogPersonId: Int?,
    onDismissRequest: () -> Unit,
    onEditPerson: (Int, String) -> Unit
) {
    if (editDialogPersonId != null) {
        NewStringDialog(
            title = stringResource(R.string.edit_person),
            hint = stringResource(R.string.imagine_you_are),
            stringCantBeEmptyHint = stringResource(R.string.name_can_t_be_empty),
            onDismissRequest = onDismissRequest,
            onAddPerson = { newName -> onEditPerson(editDialogPersonId, newName) }
        )
    }
}



@Composable
fun CreatePromptDialog(
    showCreateDialog: Boolean,
    onDismissRequest: () -> Unit,
    onAddPrompt: (String) -> Unit
) {
    if (showCreateDialog) {
        NewStringDialog(
            title = stringResource(R.string.create_prompt),
            hint = stringResource(R.string.prompt),
            stringCantBeEmptyHint = stringResource(R.string.prompt_can_t_be_empty),
            onDismissRequest = onDismissRequest,
            onAddPerson = onAddPrompt
        )
    }
}

@Composable
fun EditPromptDialog(
    promptText: String,
    editDialogPromptId: Int?,
    onDismissRequest: () -> Unit,
    onEditPrompt: (Int, String) -> Unit
) {
    if (editDialogPromptId != null) {
        NewStringDialog(
            text = promptText,
            title = stringResource(R.string.edit_prompt),
            hint = stringResource(R.string.prompt), // Or a more specific hint
            stringCantBeEmptyHint = stringResource(R.string.prompt_can_t_be_empty),
            onDismissRequest = onDismissRequest,
            onAddPerson = { newName -> onEditPrompt(editDialogPromptId, newName) }
        )
    }
}