package com.example.ailad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ailad.data.entities.MessageEntity

@Database(
    entities = [MessageEntity::class], //TODO
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

}