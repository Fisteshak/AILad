package com.example.ailad.data.repositories

import com.example.ailad.data.Error
import com.example.ailad.data.Exception
import com.example.ailad.data.Success
import com.example.ailad.domain.entities.Message
import com.example.ailad.domain.entities.asMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnswerRepository @Inject constructor(
    private val networkRepository: AnswerNetworkRepository,
    private val localRepository: AnswerLocalRepository
) {
    suspend fun fetchAnswer(prompt: String): Long {
        when (val response = networkRepository.fetchAnswer(prompt)) {
            is Error -> return -1
            is Exception -> return -1
            is Success -> {
                return localRepository.insertMessage(response.data)
            }
        }
    }

    suspend fun insertMessage(message: Message) {
        localRepository.insertMessage(message)
    }

    suspend fun getMessagesFlow(): Flow<List<Message>> {
        return localRepository.getAllMessagesFlow().map { messages ->
            messages.map {
                it.asMessage()
            }
        }
    }
}