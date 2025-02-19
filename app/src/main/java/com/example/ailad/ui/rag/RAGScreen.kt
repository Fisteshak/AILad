package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RAGScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        DropdownMenu()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DropdownMenu() {
    val options: List<String> = listOf("1", "2", "3", "4")
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(options[0])}


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },

    ) {
        TextField(
            readOnly = true,
            value = textFieldValue,
            onValueChange = {textFieldValue = it},
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }

        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        textFieldValue = option
                       // textFieldValue.setTextAndPlaceCursorAtEnd(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
