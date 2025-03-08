package com.example.ailad.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.ailad.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        LanguageSelector()
    }
}


@Composable
fun LanguageSelector() {
    var expanded by remember { mutableStateOf(false) }

    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.ru to "ru"
    ).mapKeys { stringResource(it.key) }



    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 24.dp)) {
        Row(modifier = Modifier.clickable { expanded = true }) {

            Text(
                text = stringResource(R.string.selected_language),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text =  stringResource(R.string.language),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp))

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(horizontal = 26.dp).fillMaxWidth().align(Alignment.End)

        ) {
            localeOptions.keys.forEach { selectionLocale ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        // set app locale given the user's selected locale
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(
                                localeOptions[selectionLocale]
                            )
                        )
                    },
                    text = { Text(selectionLocale) }
                )
            }
        }
    }
}


