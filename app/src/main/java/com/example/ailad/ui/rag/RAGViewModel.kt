package com.example.ailad.ui.rag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ailad.data.repositories.RAGRepository
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RAGViewModel @Inject constructor(
    private val ragRepository: RAGRepository,
) : ViewModel() {

    private val _persons = MutableStateFlow<List<Person>>(mutableListOf())
    val persons = _persons.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(mutableListOf())
    val places = _places.asStateFlow()


    init {

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


}