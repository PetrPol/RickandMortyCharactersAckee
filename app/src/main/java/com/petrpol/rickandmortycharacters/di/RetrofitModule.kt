package com.petrpol.rickandmortycharacters.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.petrpol.rickandmortycharacters.retrofit.CharactersRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/** Dependency injection module to provide retrofit interfaces */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    /** Provides Gson builder with date format for retrofit interfaces */
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    /** Provides Characters retrofit with Gson and base url for api calls */
    @Singleton
    @Provides
    fun provideCharactersRetrofit(gson:Gson): CharactersRetrofit{


        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CharactersRetrofit::class.java)
    }

}