package com.petrpol.rickandmortycharacters.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Object represents one Rick and Morty character
 *  Used also as Database Entity */
@Entity(tableName = "table_characters")
data class CharacterObject constructor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    var favourite: Boolean = false
)
