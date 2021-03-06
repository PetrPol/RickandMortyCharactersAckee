package com.petrpol.rickandmortycharacters.repositories

import android.util.Log
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.retrofit.CharactersRetrofit
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import com.petrpol.rickandmortycharacters.room.CharactersDao
import com.petrpol.rickandmortycharacters.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

/** Repository to access data from DB and Server API */
class CharactersRepository constructor(
    private val charactersRetrofit: CharactersRetrofit,
    private val charactersDao: CharactersDao
) {

    //Server api calls parameters
    private var page = 1
    private var nameQueryVal: String? = null

    /** Gets page of characters from server
     * emits actual datastate
     * @param reset - if true pages and nameQuery resets
     * @param nameQuery - if null gets all character, if is defined search by name */
    fun getCharactersPage(reset: Boolean, nameQuery: String?): Flow<DataState<CharactersResponse>> =
        channelFlow {
            send(DataState.Loading)

            try {
                if (reset) {
                    page = 1
                    nameQueryVal = nameQuery
                }

                val charactersResponse: CharactersResponse = if (nameQueryVal == null)
                    charactersRetrofit.getAllCharacters(page)
                else
                    charactersRetrofit.getAllCharactersByName(page, nameQueryVal!!)

                page++

                checkFavourites(charactersResponse)

                //Emit success
                send(DataState.Success(charactersResponse))

            }catch (e: Exception){
                //Log and emit error
                Log.w("Characters repository","Characters error: ${e.message}")
                send(DataState.Error(e))
            }
        }

    /** Support function to check if characters in page is favourite or not */
    private suspend fun checkFavourites(charactersResponse: CharactersResponse) {
        val favouriteIds = charactersDao.getFavouriteIds()

        for (character in charactersResponse.characters) {
            if (favouriteIds.contains(character.id))
                character.favourite = true
        }

    }

    /** Gets singe character info from server api
     *  Emits data state
     *  @param id - Character id to get */
    fun getSingleCharacter(id: Int): Flow<DataState<CharacterObject>> = channelFlow {
        send(DataState.Loading)

        try {
            val character = charactersRetrofit.getSingleCharacter(id)
            //Emit success
            send(DataState.Success(character))

        } catch (e: Exception) {
            //Log and emit error
            Log.w("Characters repository","Character single error: ${e.message}")
            send(DataState.Error(e))
        }
    }

    /** Removes character with selected id from favourite db */
    suspend fun removeCharacterFromFavourites(id: Int) {
        try {
            charactersDao.deleteCharacterByID(id)

        }catch (e: Exception){
            Log.w("Characters repository","Character delete error: ${e.message}")
        }

    }

    /** Adds selected character to favourite db */
    suspend fun addCharacterToFavourites(character: CharacterObject) {
        try {
            charactersDao.insert(character)

        }catch (e: Exception){
            Log.w("Characters repository","Character insert error: ${e.message}")
        }

    }

    /** Returns true if character of given id is favourite */
    suspend fun isCharacterFavourite(id: Int): Boolean {
        try {
            val favourite = charactersDao.getCharacterByID(id)
            return favourite != null

        } catch (e: Exception) {
            Log.w("Characters repository", "Character isFavourite error: ${e.message}")
        }
        return false
    }

    /** Gets list of favourite characters from DB
     *  Emits actual data state */
    fun getAllFavouriteCharacters(): Flow<DataState<List<CharacterObject>>> = channelFlow {
        send(DataState.Loading)

        try {
            val favourites = charactersDao.getCharacters()

            for (char in favourites)
                char.favourite = true

            //Emit success
            send(DataState.Success(favourites))


        }catch (e: Exception){
            Log.w("Characters repository","Character get all favourites error: ${e.message}")
            send(DataState.Error(e))
        }

    }
}