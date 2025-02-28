package com.example.ailad.ui.rag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.R
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place
import com.example.ailad.ui.chat.CommonHeader
import com.example.ailad.ui.chat.PersonCardEmpty
import com.example.ailad.ui.chat.PersonCardSmall
import com.example.ailad.ui.chat.PlaceCardSmall
import com.example.ailad.ui.chat.SmallHeader
import com.example.ailad.ui.chat.SmallHeaderPlace
import java.time.LocalDateTime

@Composable
fun RAGPanel(

    modifier: Modifier = Modifier
) {
    val viewModel: RAGViewModel = hiltViewModel()

    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val places by viewModel.places.collectAsStateWithLifecycle()

    var chosenPerson: Person? by remember { mutableStateOf(null) }
    var chosenPlace: Place? by remember { mutableStateOf(null) }
    var sortOrder: SortOrder by rememberSaveable { mutableStateOf(SortOrder.ChangeDateDesc) }
    var showFavorites by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()

    ) {

        CommonHeader(
            headerText = stringResource(R.string.templates),
            showFavorites = showFavorites,
            onSortClick = { sortOrder = it },
            onShowFavoritesClick = { showFavorites = !showFavorites },
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, end = 0.dp, start = 0.dp),
        )
        var showPersonCreateDialog by rememberSaveable { mutableStateOf(false) }

        SmallHeader(
            headerText = stringResource(R.string.persons),
            person = chosenPerson,
            onFavoriteClick = {

                val person = chosenPerson
                if (person != null) {
                    viewModel.updatePerson(person.copy(isFavorite = !person.isFavorite))
                    chosenPerson = person.copy(isFavorite = !person.isFavorite)
                    chosenPerson = person.copy(isFavorite = !person.isFavorite)
                }
            },
            onAddClick = { showPersonCreateDialog = true },
            modifier = Modifier.padding(bottom = 8.dp, end = 12.dp, start = 12.dp),
        )

        val sortedFilteredPersons = getSortedAndFilteredPersons(persons, sortOrder, showFavorites)

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier.height(140.dp)
        ) {
            item { PersonCardEmpty({ chosenPerson = null }) }
            items(sortedFilteredPersons.size) { index ->

                val item = sortedFilteredPersons[index]
                PersonCardSmall(
                    item,
                    onFavoriteClick = { viewModel.updatePerson(item.copy(isFavorite = !item.isFavorite)) },

                    onClick = { chosenPerson = item },
                    modifier = Modifier
                )
            }
        }

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

        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        var showPlaceCreateDialog by rememberSaveable { mutableStateOf(false) }

        SmallHeaderPlace(
            headerText = stringResource(R.string.places),
            person = chosenPlace,
            onFavoriteClick = {

                val place = chosenPlace
                if (place != null) {
                    viewModel.updatePlace(place.copy(isFavorite = !place.isFavorite))
                    chosenPlace = place.copy(isFavorite = !place.isFavorite)
                    chosenPlace = place.copy(isFavorite = !place.isFavorite)
                }
            },
            onAddClick = { showPlaceCreateDialog = true },
            modifier = Modifier.padding(bottom = 8.dp, end = 12.dp, start = 12.dp),
        )

        val sortedFilteredPlaces = getSortedAndFilteredPlaces(places, sortOrder, showFavorites)

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier.height(140.dp)
        ) {
            item { PersonCardEmpty({ chosenPlace = null }) }
            items(sortedFilteredPlaces.size) { index ->

                val item = sortedFilteredPlaces[index]
                PlaceCardSmall(
                    item,
                    onFavoriteClick = { viewModel.updatePlace(item.copy(isFavorite = !item.isFavorite)) },

                    onClick = { chosenPlace = item },
                    modifier = Modifier
                )
            }
        }
        if (showPlaceCreateDialog)
            CreatePlaceDialog(
                onDismissRequest = { showPlaceCreateDialog = false },
                onAddPerson = { newPersonName ->
                    val newPerson =
                        Person(0, newPersonName, LocalDateTime.now(), LocalDateTime.now(), false)
                    viewModel.insertPerson(newPerson)
                    showPlaceCreateDialog = false
                },
            )
    }

}