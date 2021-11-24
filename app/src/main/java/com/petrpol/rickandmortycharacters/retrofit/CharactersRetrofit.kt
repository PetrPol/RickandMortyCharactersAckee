package com.petrpol.rickandmortycharacters.retrofit

import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Retrofit interface to access data from server
 *  Used for characters requests */
interface CharactersRetrofit {

    /** Returns characters from given page */
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharactersResponse

    /** Returns characters from given page searched by name */
    @GET("character")
    suspend fun getAllCharactersByName(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharactersResponse

    /** Returns single character by id*/
    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): CharacterObject
}