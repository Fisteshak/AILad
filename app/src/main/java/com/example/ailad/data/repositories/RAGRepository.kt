package com.example.ailad.data.repositories

import com.example.ailad.data.db.RAGDao
import com.example.ailad.data.entities.PersonEntity
import com.example.ailad.domain.entities.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RAGRepository @Inject constructor(
    private val dao: RAGDao
) {
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
}