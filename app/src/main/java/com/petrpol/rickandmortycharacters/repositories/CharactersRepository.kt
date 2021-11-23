package com.petrpol.rickandmortycharacters.repositories

import android.util.Log
import com.petrpol.rickandmortycharacters.retrofit.CharactersRetrofit
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import com.petrpol.rickandmortycharacters.room.CharactersDao
import com.petrpol.rickandmortycharacters.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

class CharactersRepository constructor(
    private val charactersRetrofit: CharactersRetrofit,
    private val charactersDao: CharactersDao
){

    private var page = 1

    @ExperimentalCoroutinesApi
    fun loadNextPage(reset: Boolean): Flow<DataState<CharactersResponse>> = channelFlow{
        send(DataState.Loading)

        try {
            if (reset)
                page = 0

            val charactersResponse = charactersRetrofit.getAllCharacters(page)
            page++

            //Emit success
            send(DataState.Success(charactersResponse))

        }catch (e: Exception){
            //Log and emit error
            Log.w("Characters repository","Characters error: ${e.message}")
            send(DataState.Error(e))
        }
    }


}