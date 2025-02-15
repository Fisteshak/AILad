package com.example.ailad.domain.entities

import com.example.ailad.data.entities.MessageNetworkEntity
import java.time.LocalDateTime

data class Message(
    val text: String,
    val date: LocalDateTime,
    val isFavorite: Boolean,
    val isResponse: Boolean,
) {
    constructor(m: MessageNetworkEntity, isResponse: Boolean) : this(
        m.text,
        LocalDateTime.now(),
        false,
        isResponse
    )

}