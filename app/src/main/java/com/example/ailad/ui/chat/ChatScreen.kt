package com.example.ailad.ui.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeAnimationSource
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.R
import com.example.ailad.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun keyboardIsOpening(): Boolean {
    return (WindowInsets.imeAnimationTarget != WindowInsets.imeAnimationSource)
}


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ChatScreen(
    onNavigateToPrompts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = hiltViewModel()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    var showRAGPanel by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val searchBarText by viewModel.searchBarText



    Box(modifier = modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            val lazyColumnState = rememberLazyListState()
            LaunchedEffect(key1 = messages) {
                lazyColumnState.animateScrollToItem(0)
            }

            var searchText by rememberSaveable { mutableStateOf("") }
            var textFieldVisible by rememberSaveable { mutableStateOf(false) }
            val dateRangePickerState = rememberDateRangePickerState()
            SearchBar(
                text = searchText,
                onTextChange = { searchText = it },
                textFieldVisible = textFieldVisible,
                onTextFieldVisibleChange = { textFieldVisible = it; searchText = "" },
                datePickerState = dateRangePickerState,
                onDatePickerStateChange = { }

            )
            val groupedMessages = messages
                .reversed()
                .filter { it.text.contains(searchText.trim()) }
                .filter {
                    it.date.minusDays(1).toEpochSecond(ZoneOffset.UTC) * 1000 <=
                     (dateRangePickerState.selectedEndDateMillis ?: Long.MAX_VALUE)
                            &&
                     (dateRangePickerState.selectedStartDateMillis ?: Long.MIN_VALUE) <=
                    it.date.toEpochSecond(ZoneOffset.UTC) * 1000
                            || !textFieldVisible

                }
                .groupBy { it.date.toLocalDate() }
            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp), reverseLayout = true
            ) {
                groupedMessages.forEach { (date, messagesForDate) ->
//                    stickyHeader() {
//                        DateStickyHeader(date)
//                    }
                    items(messagesForDate.size) { index ->
                        MessageCard(messagesForDate[index])
                    }
                }
            }


            val keyboardVisible = WindowInsets.isImeVisible
            val locale = stringResource(R.string.locale)
            MessageBar(
                searchText = searchBarText,
                onSearchTextChange = {
                    viewModel.updateSearchBarText(it)
                    textFieldVisible = false
                    searchText = ""
                },
                onSwitchButtonClick = {
                    scope.launch {
                        if (keyboardVisible) {
                            keyboardController?.hide()
                            delay(80.milliseconds)
                        }

                        showRAGPanel = true
                    }
                },
                onSendButtonClick = {
                    viewModel.updateSearchBarText("")
                    viewModel.generate(locale, it, viewModel.chosenPerson, viewModel.chosenPlace)
                },
                onTextFieldClick = {
                    showRAGPanel = false

                },
                modifier = if (showRAGPanel) Modifier else Modifier.imePadding()
            )
            val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            if (showRAGPanel) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showRAGPanel = false
                    },
                    sheetState = bottomSheetState,
                    modifier = Modifier
                ) {
                    RAGPanel(
                        onPersonChoose = { viewModel.chosenPerson = it },
                        onPlaceChoose = { viewModel.chosenPlace = it },
                        onNavigateToPrompts = {

                            scope.launch {
                                bottomSheetState.hide()
                                showRAGPanel = false
                            }
                            onNavigateToPrompts()


                        },
                        chosenPerson = viewModel.chosenPerson,
                        chosenPlace = viewModel.chosenPlace,
                        Modifier
                    )
                }
            }
        }
    }

}


