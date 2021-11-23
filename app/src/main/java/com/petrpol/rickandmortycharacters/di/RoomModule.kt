package com.petrpol.rickandmortycharacters.di

import android.content.Context
import androidx.room.Room
import com.petrpol.rickandmortycharacters.room.CharactersDao
import com.petrpol.rickandmortycharacters.room.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Dependency injection module to provide room database and interfaces */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    /** Builds and Provides Main database */
    @Singleton
    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase{
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            MainDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /** Provides Interface to get and store characters from DB */
    @Singleton
    @Provides
    fun provideCharactersDao(mainDatabase: MainDatabase): CharactersDao {
        return mainDatabase.charactersDao()
    }
}