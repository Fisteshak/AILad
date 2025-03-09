package com.example.ailad.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ailad.R
import com.example.ailad.entities.Message
import com.example.ailad.entities.MessageStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun MessageCard(
    message: Message,
    onSavePromptClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (message.isResponse) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant
    val alignment = if (!message.isResponse) Alignment.CenterEnd else Alignment.CenterStart

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            Modifier
                .align(alignment)
                .padding(horizontal = 8.dp, vertical = 1.dp)
        ) {


            Box(
                Modifier
                    .widthIn(max = 300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .padding(top = 6.dp, bottom = 6.dp, end = 8.dp, start = 8.dp)
                    .align(if (!message.isResponse) Alignment.End else Alignment.Start)
            ) {
                when (message.status) {
                    MessageStatus.Success -> {
                        Text(
                            message.text,
                            fontSize = 16.sp
                        )
                    }

                    MessageStatus.WaitingForResponse -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
                                    .size(22.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                strokeWidth = 3.dp
                            )
                            Text(stringResource(R.string.wait))
                        }

                    }

                    MessageStatus.Error, MessageStatus.TimeoutError, MessageStatus.ConnectionError -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painterResource(R.drawable.error),
                                "Error",
                                Modifier.padding(
                                    start = 4.dp,
                                    end = 8.dp,
                                    top = 2.dp,
                                    bottom = 2.dp
                                ),
                                tint = Color.Unspecified

                            )
                            Text(
                                stringResource(
                                    when (message.status) {
                                        MessageStatus.ConnectionError -> R.string.connection_error_description
                                        MessageStatus.TimeoutError -> R.string.timeout_error_description
                                        else -> R.string.error_description

                                    }
                                )
                            )
                        }

                    }


                    // Spacer(modifier = Modifier.height(0.5.dp))
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(if (!message.isResponse) Alignment.End else Alignment.Start)
            ) {

                if (!message.isResponse) {

                    IconButton(
                        onClick = { onSavePromptClick(message.text) },
                        Modifier
                            .size(14.dp)
                            .padding(bottom = 1.dp)
                    ) {
                        Icon(painterResource(R.drawable.baseline_save_24), "save prompt")
                    }


                }
                Text(
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(message.date),
                    Modifier
                        .padding(horizontal = if (message.isResponse) 6.dp else 4.dp),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun DateStickyHeader(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}