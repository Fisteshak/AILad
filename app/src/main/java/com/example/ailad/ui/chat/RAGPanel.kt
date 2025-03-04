package com.example.ailad.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.ailad.ui.MainViewModel
import com.example.ailad.ui.rag.CreatePersonDialog
import com.example.ailad.ui.rag.CreatePlaceDialog
import com.example.ailad.ui.rag.SortOrder
import com.example.ailad.ui.rag.getSortedAndFilteredPersons
import com.example.ailad.ui.rag.getSortedAndFilteredPlaces
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RAGPanel(
    onPersonChoose: (Person?) -> Unit,
    onPlaceChoose: (Place?) -> Unit,
    onNavigateToPrompts: () -> Unit,
    chosenPerson: Person?,
    chosenPlace: Place?,

    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = hiltViewModel()

    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val places by viewModel.places.collectAsStateWithLifecycle()


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
            onSwitchButtonClick = { onNavigateToPrompts() },
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, end = 0.dp, start = 0.dp),
        )


        var showPersonCreateDialog by rememberSaveable { mutableStateOf(false) }

        SmallHeader(
            headerText = stringResource(R.string.persons),
            person = chosenPerson,
            onFavoriteClick = {
                if (chosenPerson != null) {
                    viewModel.updatePerson(chosenPerson.copy(isFavorite = !chosenPerson.isFavorite))
                    onPersonChoose(chosenPerson.copy(isFavorite = !chosenPerson.isFavorite))
                }
            },
            onAddClick = { showPersonCreateDialog = true },
            modifier = Modifier.padding(bottom = 8.dp, end = 12.dp, start = 12.dp),
        )

        val sortedFilteredPersons =
            getSortedAndFilteredPersons(persons, sortOrder, showFavorites)

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier.height(140.dp)
        ) {
            item {
                PersonCardEmpty({
                    onPersonChoose(null)
                })
            }
            items(sortedFilteredPersons.size) { index ->

                val item = sortedFilteredPersons[index]
                PersonCardSmall(
                    item,
                    onFavoriteClick = {
                        if (item.id == chosenPerson?.id) onPersonChoose(item.copy(isFavorite = !item.isFavorite))
                        viewModel.updatePerson(item.copy(isFavorite = !item.isFavorite))

                    },

                    onClick = {
                        onPersonChoose(item)
                    },
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
            // Use SmallHeader for places as well
            headerText = stringResource(R.string.places),
            person = chosenPlace, // Pass chosenPlace instead of chosenPerson
            onFavoriteClick = {
                val place = chosenPlace
                if (place != null) {
                    viewModel.updatePlace(place.copy(isFavorite = !place.isFavorite))
                    onPlaceChoose(place.copy(isFavorite = !place.isFavorite))
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
            item {
                PersonCardEmpty({
                    onPlaceChoose(null) // Pass null to clear selection
                })
            }
            items(sortedFilteredPlaces.size) { index ->
                val item = sortedFilteredPlaces[index]
                PlaceCardSmall(
                    item,
                    onFavoriteClick = {
                        if (item.id == chosenPlace?.id) onPlaceChoose(item.copy(isFavorite = !item.isFavorite))
                        viewModel.updatePlace(item.copy(isFavorite = !item.isFavorite))
                    },
                    onClick = {
                        onPlaceChoose(item)
                    },
                    modifier = Modifier
                )
            }
        }
        if (showPlaceCreateDialog)
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
}