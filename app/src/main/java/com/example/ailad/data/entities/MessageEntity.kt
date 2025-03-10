package com.example.ailad.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ailad.entities.Message
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val date: Long,
    @ColumnInfo("is_response")
    val isResponse: Boolean,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(defaultValue = "0")
    val status: Int
)

fun MessageNetworkEntity.asEntity(status: Int): MessageEntity {
    return MessageEntity(
        0,
        text,
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        true,
        false,
        status
    )
}

fun Message.asEntity(): MessageEntity {
    return MessageEntity(
        id,
        text,
        date.toEpochSecond(ZoneOffset.UTC),
        isResponse,
        isFavorite,
        status.id
    )
}