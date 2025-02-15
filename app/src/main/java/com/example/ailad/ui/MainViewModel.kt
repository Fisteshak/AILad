package com.example.ailad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.Error
import com.example.ailad.data.Exception
import com.example.ailad.data.Success
import com.example.ailad.data.repositories.AnswerNetworkRepository
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
    private val repo: AnswerNetworkRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(mutableListOf())
    val messages = _messages.asStateFlow()

    fun generate(prompt: String) {
        viewModelScope.launch {
            _messages.update { it.plus(Message(prompt, LocalDateTime.now(), false, false)) }
            when (val messageResponse = repo.getAnswer(prompt)) {
                is Success -> {
                    _messages.update { it.plus(Message(messageResponse.data, true)) }

                }

                is Error -> {}
                is Exception -> {}
            }

        }
    }


}