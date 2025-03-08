package com.example.ailad.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R

@Composable
fun MessageBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onTextFieldClick: () -> Unit,
    onSendButtonClick: (String) -> Unit,
    onSwitchButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier.fillMaxWidth()) {
        HorizontalDivider(Modifier.fillMaxWidth().padding(vertical = 0.dp), thickness = 2.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onSwitchButtonClick() }) {
                Icon(painterResource(R.drawable.add), contentDescription = "Templates")
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
                val trimmedText = searchText.trim()
                if (trimmedText.isNotEmpty()) {
                    onSendButtonClick(trimmedText)
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }


    }
}