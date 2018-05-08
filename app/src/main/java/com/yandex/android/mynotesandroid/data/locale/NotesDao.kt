package com.yandex.android.mynotesandroid.data.locale

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.yandex.android.mynotesandroid.domain.Note
import io.reactivex.Flowable

@Dao
abstract class NotesDao {
    companion object {
        const val TABLE_NAME = "notes"
    }

    interface Columns {
        companion object {
            const val ID = "id"
            const val TITLE = "title"
            const val CONTENT = "content"
            const val DATE = "date"
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(notes: List<Note>)

    @NonNull
    @Query("SELECT * FROM $TABLE_NAME ORDER BY ${Columns.DATE}")
    abstract fun getNotesList() : Flowable<List<Note>>

    @NonNull
    @Query("SELECT * FROM $TABLE_NAME WHERE id = :uid")
    abstract fun getNote(uid: String) : Flowable<List<Note>>

    @Delete
    abstract fun deleteNote(note: Note)

}