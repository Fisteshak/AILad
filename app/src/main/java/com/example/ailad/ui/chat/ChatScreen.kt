package com.example.ailad.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ailad.domain.entities.Message
import com.example.ailad.ui.MainViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val messages by viewModel.messages.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.imePadding()
        ) {
            Text("This is chat screen")

            LazyColumn(Modifier.weight(1f), reverseLayout = true) {
                items(messages.size) { index ->
                    MessageBox(messages.reversed()[index])
                }
            }

            var text by rememberSaveable { mutableStateOf("") }
            Row(Modifier) {
                TextField(text, { text = it })
                Button(
                    { viewModel.generate(text) }, modifier = Modifier
                        .requiredWidth(100.dp)
                ) { Text("generate") }
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