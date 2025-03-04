package com.example.ailad.entities

import com.example.ailad.data.entities.PromptEntity
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Prompt(
    val id: Int,
    val name: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val changeDate: LocalDateTime = LocalDateTime.now(),
    var isFavorite: Boolean = false
) {
    constructor(p: PromptEntity) : this(
        p.id,
        p.name,
        LocalDateTime.ofEpochSecond(p.creationDate, 0, ZoneOffset.UTC),
        LocalDateTime.ofEpochSecond(p.changeDate, 0, ZoneOffset.UTC),
        p.isFavorite
    )
}
