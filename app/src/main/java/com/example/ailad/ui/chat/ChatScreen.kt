package com.example.ailad.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.entities.Message
import com.example.ailad.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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

            LazyColumn(
                Modifier
                    .weight(1f)
                    .height(10.dp), reverseLayout = true
            ) {
                items(messages.size) { index ->
                    MessageBox(messages.reversed()[index])
                }
            }

            val keyboardVisible = WindowInsets.isImeVisible
            SearchBar(
                searchText = searchBarText,
                onSearchTextChange = { viewModel.updateSearchBarText(it) },
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
                    viewModel.generate(it, viewModel.chosenPerson, viewModel.chosenPlace)
                },
                onTextFieldClick = {
                    showRAGPanel = false

                },
                modifier = if (showRAGPanel) Modifier else Modifier.imePadding()
            )
            val bottomSheetState = rememberModalBottomSheetState()
            if (showRAGPanel) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showRAGPanel = false
                    },
                    sheetState = bottomSheetState,
                    modifier = Modifier.imePadding()
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


@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onTextFieldClick: () -> Unit,
    onSendButtonClick: (String) -> Unit,
    onSwitchButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onSwitchButtonClick() }) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "Emoji")
            }


            BasicTextField(

                value = searchText,
                onValueChange = { onSearchTextChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                singleLine = false,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onTextFieldClick()
                                }
                            }
                        }
                    }
                //  placeholder = { Text("Search") }
            )

            IconButton(onClick = {
                // Handle send action here (e.g., perform search)
                onSendButtonClick(searchText)
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }


    }
}


@Composable
fun MessageBox(message: Message, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier
                .align(if (message.isResponse) Alignment.CenterEnd else Alignment.CenterStart)
                .padding(4.dp)
                .width(300.dp)

        ) {

            Text(
                DateTimeFormatter.ISO_DATE_TIME.format(message.date),
                Modifier.padding(bottom = 3.dp)
            )
            Text(
                message.text,
                Modifier
            )
        }
    }
}
