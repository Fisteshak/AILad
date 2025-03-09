package com.example.ailad.ui.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    textFieldVisible: Boolean,
    onTextFieldVisibleChange: (Boolean) -> Unit,
    datePickerState: DateRangePickerState,
    onDatePickerStateChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = textFieldVisible) {
        onTextFieldVisibleChange(false)

    }
    val focusRequester = remember { FocusRequester() }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 2.dp
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    androidx.compose.animation.AnimatedVisibility(
                        visible = textFieldVisible,
                        modifier = Modifier
                    ) {
                        TextField(
                            value = text,
                            onValueChange = { onTextChange(it) },
                            placeholder = { Text(stringResource(R.string.search)) },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)

                        )
                    }

                }
                val coroutineScope = rememberCoroutineScope()
                if (textFieldVisible) {
                    val isRangeSelected = datePickerState.selectedStartDateMillis != null &&
                            datePickerState.selectedEndDateMillis != null

                    IconButton(
                        onClick = { showDatePickerDialog = true },
                        colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                            containerColor = if (isRangeSelected) Color.Green else Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Pick Date",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                } else {

                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (!textFieldVisible) {
                                delay(50.milliseconds)
                                focusRequester.requestFocus()
                            }

                        }
                        onTextFieldVisibleChange(!textFieldVisible)

                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
            if (showDatePickerDialog)
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePickerDialog = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePickerDialog = false

                        }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    dismissButton = {

                        TextButton(onClick = {
                            showDatePickerDialog = false; onDatePickerStateChange()
                        }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DateRangePicker(
                        state = datePickerState,
                        title = {
                            Text(
                                text = stringResource(R.string.select_date_range)
                            )
                        },
                        showModeToggle = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(16.dp)
                    )
                }

            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp), thickness = 2.dp
            )
        }
    }
}
