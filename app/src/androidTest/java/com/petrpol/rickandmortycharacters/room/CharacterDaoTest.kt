package com.petrpol.rickandmortycharacters.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.model.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Tests for database access by characterDao */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class CharacterDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var dao: CharactersDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.charactersDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCharacter() = runBlockingTest {
        val character = CharacterObject(
            0, "name", "status", "species", "type", "gander", Location("origin", "originURL"),
            Location("location", "locationURL"), "image", true
        )
        dao.insert(character)

        val characterFromDB = dao.getCharacterByID(character.id)
        assertThat(characterFromDB).isNotNull()
        assertThat(characterFromDB!!.id).isEqualTo(character.id)
        assertThat(characterFromDB.name).isEqualTo(character.name)
        assertThat(characterFromDB.status).isEqualTo(character.status)
        assertThat(characterFromDB.location).isEqualTo(character.location)
        assertThat(characterFromDB.favourite).isEqualTo(true)
    }

    @Test
    fun deleteCharacterByID() = runBlockingTest {
        val character = CharacterObject(
            0, "name", "status", "species", "type", "gander", Location("origin", "originURL"),
            Location("location", "locationURL"), "image", true
        )

        dao.insert(character)

        dao.deleteCharacterByID(character.id)

        val characterFromDB = dao.getCharacterByID(character.id)
        assertThat(characterFromDB).isNull()
    }

    @Test
    fun getFavouriteIds() = runBlockingTest {
        val character = CharacterObject(
            0, "name", "status", "species", "type", "gander", Location("origin", "originURL"),
            Location("location", "locationURL"), "image", true
        )
        val character2 = CharacterObject(
            5, "name", "status", "species", "type", "gander", Location("origin", "originURL"),
            Location("location", "locationURL"), "image", true
        )

        dao.insert(character)
        dao.insert(character2)

        val characterIdsFromDB = dao.getFavouriteIds()

        assertThat(characterIdsFromDB.size).isEqualTo(2)
        assertThat(characterIdsFromDB).contains(0)
        assertThat(characterIdsFromDB).contains(5)
    }
}