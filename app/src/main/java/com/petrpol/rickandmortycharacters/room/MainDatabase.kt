package com.petrpol.rickandmortycharacters.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.room.typeConverters.RoomTypeConverters

/** Room main database  */
@Database(entities = [CharacterObject::class], version = 3, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class MainDatabase: RoomDatabase(){

    //Dao getters
    abstract fun charactersDao(): CharactersDao

    companion object{
        const val DATABASE_NAME:String = "mainDatabase"
    }
}