package com.example.ailad.data.repositories

import com.example.ailad.data.db.RAGDao
import com.example.ailad.data.entities.PersonEntity
import com.example.ailad.data.entities.PlaceEntity
import com.example.ailad.data.entities.PromptEntity
import com.example.ailad.entities.Person
import com.example.ailad.entities.Place
import com.example.ailad.entities.Prompt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RAGRepository @Inject constructor(
    private val dao: RAGDao
) {
    // Person operations
    suspend fun insertPerson(person: Person) {
        dao.insertPerson(PersonEntity(person))
    }

    fun getPersonsFlow(): Flow<List<Person>> {
        return dao.getPersonsFlow().map { list ->
            list.map { Person(it) }
        }
    }

    suspend fun updatePerson(person: Person) {
        dao.updatePerson(PersonEntity(person))
    }

    suspend fun deletePerson(person: Person) {
        dao.deletePerson(PersonEntity(person))
    }

    // Place operations
    suspend fun insertPlace(place: Place) {
        dao.insertPlace(PlaceEntity(place))
    }

    fun getPlacesFlow(): Flow<List<Place>> {
        return dao.getPlacesFlow().map { list ->
            list.map { Place(it) }
        }
    }

    suspend fun updatePlace(place: Place) {
        dao.updatePlace(PlaceEntity(place))
    }

    suspend fun deletePlace(place: Place) {
        dao.deletePlace(PlaceEntity(place))
    }

    // Prompt operations
    suspend fun insertPrompt(prompt: Prompt) {
        dao.insertPrompt(PromptEntity(prompt))
    }

    fun getPromptsFlow(): Flow<List<Prompt>> {
        return dao.getPromptsFlow().map { list ->
            list.map { Prompt(it) }
        }
    }

    suspend fun updatePrompt(prompt: Prompt) {
        dao.updatePrompt(PromptEntity(prompt))
    }

    suspend fun deletePrompt(prompt: Prompt) {
        dao.deletePrompt(PromptEntity(prompt))
    }
}