package com.yandex.android.mynotesandroid.di

import android.arch.persistence.room.Room
import android.content.Context
import com.yandex.android.mynotesandroid.data.locale.NotesDao
import com.yandex.android.mynotesandroid.data.locale.NotesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext : Context) {

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


}