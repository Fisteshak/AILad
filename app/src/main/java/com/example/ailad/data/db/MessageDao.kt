package com.example.ailad.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ailad.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    suspend fun getAll(): List<MessageEntity>

    @Query("SELECT * FROM message")
    fun getAllFlow(): Flow<List<MessageEntity>>


    @Query("SELECT * FROM message WHERE id=:id")
    suspend fun getMessageById(id: Int): MessageEntity

    @Insert
    suspend fun insertMessage(messageEntity: MessageEntity): Long


}