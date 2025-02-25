package com.example.ailad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ailad.data.entities.MessageEntity
import com.example.ailad.data.entities.PersonEntity
import com.example.ailad.data.entities.PlaceEntity

@Database(
    entities = [MessageEntity::class, PersonEntity::class, PlaceEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    abstract fun RAGDao(): RAGDao
}