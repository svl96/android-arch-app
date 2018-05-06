package com.yandex.android.mynotesandroid.di

import android.arch.persistence.room.Room
import android.content.Context
import com.yandex.android.mynotesandroid.data.locale.LocalRepositoryImpl
import com.yandex.android.mynotesandroid.data.locale.NotesDao
import com.yandex.android.mynotesandroid.data.locale.NotesDatabase
import com.yandex.android.mynotesandroid.domain.LoadNotesUseCase
import com.yandex.android.mynotesandroid.domain.LocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext : Context) {

    @Provides
    fun context() : Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideNotesDatabase() : NotesDatabase {
        return Room.databaseBuilder(
                appContext,
                NotesDatabase::class.java,
                NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNotesDao(notesDatabase : NotesDatabase) : NotesDao {
        return notesDatabase.notesDoa()
    }

    @Provides
    @Singleton
    fun provideLoadNotesUseCase(localRepository: LocalRepository) : LoadNotesUseCase {
        return LoadNotesUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(notesDao: NotesDao) : LocalRepository {
        return LocalRepositoryImpl(notesDao)
    }

}