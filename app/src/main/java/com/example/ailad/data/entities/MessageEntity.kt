package com.example.ailad.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey
    val id: Int,
    val text: String,
    val date: Long,
    @ColumnInfo("is_response")
    val isResponse: Boolean,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean,
)