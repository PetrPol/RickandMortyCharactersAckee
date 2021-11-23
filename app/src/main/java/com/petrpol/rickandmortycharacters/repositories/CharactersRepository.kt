package com.petrpol.rickandmortycharacters.repositories

import android.util.Log
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.retrofit.CharactersRetrofit
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import com.petrpol.rickandmortycharacters.room.CharactersDao
import com.petrpol.rickandmortycharacters.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class CharactersRepository constructor(
    private val charactersRetrofit: CharactersRetrofit,
    private val charactersDao: CharactersDao
){

    private var page = 1
    private var nameQueryVal:String? = null

    @ExperimentalCoroutinesApi
    fun getCharactersPage(reset: Boolean,nameQuery: String?): Flow<DataState<CharactersResponse>> = channelFlow{
        send(DataState.Loading)

        try {
            if (reset) {
                page = 0
                nameQueryVal = nameQuery
            }

            val charactersResponse : CharactersResponse = if (nameQueryVal==null)
                charactersRetrofit.getAllCharacters(page)
            else
                charactersRetrofit.getAllCharactersByName(page, nameQueryVal!!)

            page++

            //Emit success
            send(DataState.Success(charactersResponse))

        }catch (e: Exception){
            //Log and emit error
            Log.w("Characters repository","Characters error: ${e.message}")
            send(DataState.Error(e))
        }
    }

    @ExperimentalCoroutinesApi
    fun getSingleCharacter(id: Int) : Flow<DataState<CharacterObject>> = channelFlow {
        send(DataState.Loading)

        try {
            val character = charactersRetrofit.getSingleCharacter(id)
            Log.i("TEST",character.name)
            //Emit success
            send(DataState.Success(character))

        }catch (e: Exception){
            //Log and emit error
            Log.w("Characters repository","Character single error: ${e.message}")
            send(DataState.Error(e))
        }
    }
}