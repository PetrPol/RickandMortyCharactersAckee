package com.petrpol.rickandmortycharacters.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petrpol.rickandmortycharacters.model.CharacterObject

/** Room database dao for Credentials */
@Dao
interface CharactersDao {

    /** Stores CharacterObject to DB */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterObject)

    /** Gets all characters form DB */
    @Query("SELECT * FROM table_characters")
    suspend fun getCharacters(): List<CharacterObject>

    /** Get character of given ID from db. Returns null if don't exist */
    @Query("SELECT * FROM table_characters WHERE id = :character_id")
    suspend fun getCharacterByID(character_id:Int): CharacterObject?

    /** Deletes character with selected id from DB */
    @Query("DELETE FROM table_characters WHERE id = :character_id")
    suspend fun deleteCharacterByID(character_id : Int)

    /** Gets list of Ids of all favourite characters */
    @Query("SELECT id FROM table_characters")
    suspend fun getFavouriteIds(): List<Int>

}