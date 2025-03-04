package com.example.ailad.data.repositories

import android.util.Log
import com.example.ailad.data.Error
import com.example.ailad.data.Exception
import com.example.ailad.data.Success
import com.example.ailad.entities.Message
import com.example.ailad.entities.Person
import com.example.ailad.entities.asMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class AnswerRepository @Inject constructor(
    private val networkRepository: AnswerNetworkRepository,
    private val localRepository: AnswerLocalRepository
) {
    suspend fun fetchAnswer(prompt: String): Long {
        Log.d("AnswerRepository", "fetchingAnswer: $prompt ")
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

val testData = listOf(
    Person(
        0,
        "Newton",
        LocalDateTime.of(2025, 1, 1, 12, 30),
        LocalDateTime.of(2025, 1, 1, 12, 30),
        true
    ),
    Person(
        1,
        "Einstein",
        LocalDateTime.of(2024, 5, 10, 8, 15),
        LocalDateTime.of(2024, 5, 12, 10, 0),
        false
    ),
    Person(
        2,
        "Marie Curie",
        LocalDateTime.of(2023, 11, 20, 10, 0),
        LocalDateTime.of(2023, 11, 22, 14, 30),
        true
    ),
    Person(
        3,
        "Galileo",
        LocalDateTime.of(2025, 3, 15, 14, 45),
        LocalDateTime.of(2025, 3, 18, 9, 15),
        false
    ),
    Person(
        4,
        "Hawking",
        LocalDateTime.of(2024, 8, 5, 9, 20),
        LocalDateTime.of(2024, 8, 7, 16, 40),
        true
    ),
    Person(
        5,
        "Tesla",
        LocalDateTime.of(2023, 12, 1, 11, 10),
        LocalDateTime.of(2023, 12, 3, 12, 50),
        false
    ),
    Person(
        6,
        "Edison",
        LocalDateTime.of(2025, 2, 20, 16, 30),
        LocalDateTime.of(2025, 2, 22, 11, 10),
        true
    ),
    Person(
        7,
        "Archimedes",
        LocalDateTime.of(2024, 7, 18, 7, 55),
        LocalDateTime.of(2024, 7, 20, 15, 25),
        false
    ),
    Person(
        8,
        "Euclid",
        LocalDateTime.of(2023, 10, 12, 13, 25),
        LocalDateTime.of(2023, 10, 14, 8, 55),
        true
    ),
    Person(
        9,
        "Da Vinci",
        LocalDateTime.of(2025, 4, 5, 15, 15),
        LocalDateTime.of(2025, 4, 7, 12, 35),
        false
    ),
    Person(
        10,
        "Michelangelo",
        LocalDateTime.of(2024, 9, 8, 10, 50),
        LocalDateTime.of(2024, 9, 10, 17, 10),
        true
    )
)