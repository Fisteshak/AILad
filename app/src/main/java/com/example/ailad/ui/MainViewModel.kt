package com.example.ailad.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.repositories.AnswerRepository
import com.example.ailad.data.repositories.RAGRepository
import com.example.ailad.entities.Message
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place
import com.example.ailad.entities.Prompt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ragRepository: RAGRepository,
    private val messageRepository: AnswerRepository,

    ) : ViewModel() {

    private val _persons = MutableStateFlow<List<Person>>(mutableListOf())
    val persons = _persons.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(mutableListOf())
    val places = _places.asStateFlow()

    private val _prompts = MutableStateFlow<List<Prompt>>(mutableListOf())
    val prompts = _prompts.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(mutableListOf())
    val messages = _messages.asStateFlow()

    var searchBarText = mutableStateOf("")

    var selectedTab = mutableIntStateOf(0)

    var chosenPerson: Person? by mutableStateOf(null)
    var chosenPlace: Place? by mutableStateOf(null)

    init {

        Log.d("MainViewModel", "creating ")
        // persons update coroutine
        viewModelScope.launch {
            ragRepository.getPersonsFlow().collect { new ->
                _persons.update { new }
            }
        }

        // places update coroutine
        viewModelScope.launch {
            ragRepository.getPlacesFlow().collect { new ->
                _places.update { new }
            }
        }

        // prompts update coroutine
        viewModelScope.launch {
            ragRepository.getPromptsFlow().collect { new ->
                _prompts.update { new }
            }
        }

        // messages update coroutine
        viewModelScope.launch {
            messageRepository.getMessagesFlow().collect { new ->
                _messages.update { new }
            }
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

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            ragRepository.updatePlace(place)
        }
    }

    fun insertPlace(place: Place) {
        viewModelScope.launch {
            ragRepository.insertPlace(place)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            ragRepository.deletePlace(place)
        }
    }

    fun updatePrompt(prompt: Prompt) {
        viewModelScope.launch {
            ragRepository.updatePrompt(prompt)
        }
    }

    fun insertPrompt(prompt: Prompt) {
        viewModelScope.launch {
            ragRepository.insertPrompt(prompt)
        }
    }

    fun deletePrompt(prompt: Prompt) {
        viewModelScope.launch {
            ragRepository.deletePrompt(prompt)
        }
    }


    fun generate(prompt: String, person: Person? = null, place: Place? = null
    ) {

        var ragPrompt = ""
        if (person != null) {
            ragPrompt += "Imagine you are ${person.name}"
            if (place != null) ragPrompt += " at ${place.name}. " else ragPrompt += ". "
        } else if (place != null) {
            ragPrompt += "Imagine you are at ${place.name}. "
        }

        ragPrompt += prompt

        viewModelScope.launch {
            messageRepository.insertMessage(Message(ragPrompt, LocalDateTime.now(), false, false))
            messageRepository.fetchAnswer(ragPrompt)
        }
    }

    fun generate(promptId: Int) {
        val prompt = prompts.value.find { it.id == promptId }
        if (prompt != null)
            generate(prompt.name, chosenPerson, chosenPlace)
    }

    fun loadPromptToSearchBar(promptId: Int) {
        val prompt = prompts.value.find { it.id == promptId }
        if (prompt != null)
            searchBarText.value = prompt.name
    }

    fun updateSearchBarText(text: String) {
        searchBarText.value = text
    }
 }