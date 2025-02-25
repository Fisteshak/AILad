package com.example.ailad.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.entities.Message
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val viewModel: ChatViewModel = hiltViewModel()
    val messages by viewModel.messages.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //modifier = Modifier.imePadding()
        ) {
            Text("This is chat screen")

            LazyColumn(Modifier.weight(1f), reverseLayout = true) {
                items(messages.size) { index ->
                    MessageBox(messages.reversed()[index])
                }
            }
            Box(Modifier.height(10.dp).weight(1f))

            var text by rememberSaveable { mutableStateOf("") }
            SearchBar()



        }
    }

}

@Preview
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    var showEmojiPanel by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { showEmojiPanel = !showEmojiPanel }) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "Emoji")
            }


            BasicTextField(

                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                singleLine = true,
              //  placeholder = { Text("Search") }
            )

            IconButton(onClick = {
                // Handle send action here (e.g., perform search)
                println("Searching for: $searchText")
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }

        AnimatedVisibility(
            visible = showEmojiPanel,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(200)
            ) + fadeIn() + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Bottom
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(200)
            ) + fadeOut() + shrinkVertically(
                // Expand from the top.
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Height of emoji panel
                    .background(Color.Green) // Or any other color
                    .animateEnterExit()
            ) {
                // Placeholder for emoji panel
                Text("Emoji Panel (Placeholder)", modifier = Modifier.align(Alignment.Center))
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