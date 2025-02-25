package com.example.ailad.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.repositories.AnswerRepository
import com.example.ailad.data.repositories.RAGRepository
import com.example.ailad.entities.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: AnswerRepository,
    private val ragRepository: RAGRepository,
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(mutableListOf())
    val messages = _messages.asStateFlow()

    init {
        // messages update coroutine
        viewModelScope.launch {
            messageRepository.getMessagesFlow().collect { new ->
                _messages.update { new }
            }
        }

    }

    fun generate(prompt: String) {
        viewModelScope.launch {
            messageRepository.insertMessage(Message(prompt, LocalDateTime.now(), false, false))
            messageRepository.fetchAnswer(prompt)
        }
    }



}