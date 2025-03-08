package com.example.ailad.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ailad.data.entities.MessageEntity
import com.example.ailad.data.entities.PersonEntity
import com.example.ailad.data.entities.PlaceEntity
import com.example.ailad.data.entities.PromptEntity

@Database(
    entities = [MessageEntity::class, PersonEntity::class, PlaceEntity::class, PromptEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ],

    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    abstract fun RAGDao(): RAGDao
}