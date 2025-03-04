package com.example.ailad.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ailad.data.entities.PersonEntity
import com.example.ailad.data.entities.PlaceEntity
import com.example.ailad.data.entities.PromptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RAGDao {

    @Query("SELECT * FROM person")
    fun getPersonsFlow(): Flow<List<PersonEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE) // ?? does not work without conflictStrategy
    suspend fun updatePerson(person: PersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Delete
    suspend fun deletePerson(person: PersonEntity)

    @Query("SELECT * FROM place")
    fun getPlacesFlow(): Flow<List<PlaceEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE) // ?? does not work without conflictStrategy
    suspend fun updatePlace(place: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    @Delete
    suspend fun deletePlace(place: PlaceEntity)


    @Query("SELECT * FROM prompt") // Changed to prompt table
    fun getPromptsFlow(): Flow<List<PromptEntity>> // Changed to PromptEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePrompt(prompt: PromptEntity) // Changed to PromptEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrompt(prompt: PromptEntity) // Changed to PromptEntity

    @Delete
    suspend fun deletePrompt(prompt: PromptEntity) // Changed to PromptEntity

}