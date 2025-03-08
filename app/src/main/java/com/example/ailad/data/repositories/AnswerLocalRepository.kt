package com.example.ailad.data.repositories

import com.example.ailad.data.db.MessageDao
import com.example.ailad.data.entities.MessageEntity
import com.example.ailad.data.entities.asEntity
import com.example.ailad.entities.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnswerLocalRepository @Inject constructor(
    private val dao: MessageDao
) {

    suspend fun insertMessage(message: Message): Long {
        return dao.insertMessage(message.asEntity())
    }

    suspend fun deleteMessageById(messageId: Int) {
        return dao.deleteMessageById(messageId)
    }
    suspend fun deleteWaitingForResponseMessages() {
        dao.deleteWaitingForResponseMessages()
    }



    suspend fun insertMessage(message: MessageEntity): Long {
        return dao.insertMessage(message)
    }
    suspend fun updateMessage(message: MessageEntity): Long {
        return dao.updateMessage(message).toLong()
    }

    suspend fun getAllMessages(): List<MessageEntity> {
        return dao.getAll()
    }

    suspend fun getAllMessagesFlow(): Flow<List<MessageEntity>> {
        return dao.getAllFlow()
    }

    suspend fun getMessageById(id: Int): MessageEntity {
        return dao.getMessageById(id)
    }


}