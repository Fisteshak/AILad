package com.example.ailad.entities

import com.example.ailad.data.entities.MessageEntity
import com.example.ailad.data.entities.MessageNetworkEntity
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Message(
    val text: String,
    val date: LocalDateTime,
    val isFavorite: Boolean,
    val isResponse: Boolean,
    val status: MessageStatus = MessageStatus.Success,
    val id: Int = 0
) {
    constructor(m: MessageNetworkEntity, isResponse: Boolean = true, status: MessageStatus) : this(
        m.text,
        LocalDateTime.now(),
        false,
        isResponse,
        status
    )

}

fun MessageEntity.asMessage(): Message {
    return Message(
        text,
        LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC),
        isFavorite,
        isResponse,
        MessageStatus.entries.first { it.id == status }
    )
}