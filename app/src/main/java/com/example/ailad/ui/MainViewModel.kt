package com.example.ailad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.repositories.AnswerRepository
import com.example.ailad.domain.entities.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: AnswerRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(mutableListOf())
    val messages = _messages.asStateFlow()


    init {
        // data update coroutine
        viewModelScope.launch {
            repo.getMessagesFlow().collect { new ->
                _messages.update { new }
            }
        }
    }

    fun generate(prompt: String) {
        viewModelScope.launch {
            repo.insertMessage(Message(prompt, LocalDateTime.now(), false, false))

            repo.fetchAnswer(prompt)
        }
    }



}