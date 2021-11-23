package com.petrpol.rickandmortycharacters.di

import com.petrpol.rickandmortycharacters.repositories.CharactersRepository
import com.petrpol.rickandmortycharacters.retrofit.CharactersRetrofit
import com.petrpol.rickandmortycharacters.room.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Dependency injection module to provide repositories */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /** Provides Characters repository with all dependencies */
    @Singleton
    @Provides
    fun provideCharactersRepository(
        charactersRetrofit: CharactersRetrofit,
        charactersDao: CharactersDao,
    ): CharactersRepository{
        return CharactersRepository(charactersRetrofit, charactersDao)
    }
}