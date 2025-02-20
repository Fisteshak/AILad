package com.example.ailad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.repositories.AnswerRepository
import com.example.ailad.data.repositories.RAGRepository
import com.example.ailad.domain.entities.Message
import com.example.ailad.domain.entities.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val messageRepository: AnswerRepository,
    private val ragRepository: RAGRepository

) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(mutableListOf())
    val messages = _messages.asStateFlow()

    private val _persons = MutableStateFlow<List<Person>>(mutableListOf())
    val persons = _persons.asStateFlow()

    init {
        // messages update coroutine
        viewModelScope.launch {
            messageRepository.getMessagesFlow().collect { new ->
                _messages.update { new }
            }
        }

        // persons update coroutine
        viewModelScope.launch {
            ragRepository.getPersonsFlow().collect { new ->
                _persons.update { new }
            }
        }
    }

    fun generate(prompt: String) {
        viewModelScope.launch {
            messageRepository.insertMessage(Message(prompt, LocalDateTime.now(), false, false))
            messageRepository.fetchAnswer(prompt)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            ragRepository.updatePerson(person)
        }
    }


    fun insertPerson(person: Person) {
        viewModelScope.launch {
            ragRepository.insertPerson(person)

        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            ragRepository.deletePerson(person)
        }
    }


}