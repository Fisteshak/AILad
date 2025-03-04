package com.example.ailad.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ailad.entities.Prompt
import java.time.ZoneOffset

@Entity(tableName = "prompt")
data class PromptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo("creation_date")
    val creationDate: Long,
    @ColumnInfo("change_date")
    val changeDate: Long,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean = false
) {
    constructor(p: Prompt) : this(
        p.id,
        p.name,
        p.creationDate.toEpochSecond(ZoneOffset.UTC),
        p.changeDate.toEpochSecond(ZoneOffset.UTC),
        p.isFavorite
    )
}