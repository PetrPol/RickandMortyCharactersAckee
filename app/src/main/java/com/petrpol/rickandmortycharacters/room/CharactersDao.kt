package com.petrpol.rickandmortycharacters.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petrpol.rickandmortycharacters.model.CharacterObject

/** Room database dao for Credentials */
@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterObject)

    @Query("SELECT * FROM table_characters")
    suspend fun getCharacters(): List<CharacterObject>

    @Query("SELECT * FROM table_characters WHERE id = :character_id")
    suspend fun getCharacterByID(character_id:Int): CharacterObject?

    @Query("DELETE FROM table_characters WHERE id = :character_id")
    suspend fun deleteCharacterByID(character_id : Int)

    @Query("SELECT id FROM table_characters")
    suspend fun getFavouriteIds(): List<Int>

}