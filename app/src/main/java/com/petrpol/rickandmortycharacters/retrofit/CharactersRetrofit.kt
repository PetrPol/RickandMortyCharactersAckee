package com.petrpol.rickandmortycharacters.retrofit

import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import retrofit2.http.*

/** Retrofit interface to access data from server
 *  Used for login requests */
interface CharactersRetrofit {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharactersResponse

    @GET("character")
    suspend fun getAllCharactersByName(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): CharacterObject
}