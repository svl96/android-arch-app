package com.yandex.android.mynotesandroid.data.locale

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.yandex.android.mynotesandroid.domain.Note

@Database(version = NotesDatabase.DATABASE_VERSION,
        entities = [(Note::class)]
        )
abstract class NotesDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "notes_new.db"
        const val DATABASE_VERSION = 1
    }

    abstract fun notesDoa() : NotesDao
}