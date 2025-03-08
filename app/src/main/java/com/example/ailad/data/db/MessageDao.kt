package com.example.ailad.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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


    // i don't know why it returns long in insert and int in update, but it requires to do that
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageEntity: MessageEntity): Long

    @Update()
    suspend fun updateMessage(messageEntity: MessageEntity): Int

    @Query("DELETE FROM message WHERE id = :id")
    suspend fun deleteMessageById(id: Int)


    // MessageStatus.WaitingForResponse.id == 2
    @Query("DELETE FROM message WHERE status = 2")
    suspend fun deleteWaitingForResponseMessages()


}
