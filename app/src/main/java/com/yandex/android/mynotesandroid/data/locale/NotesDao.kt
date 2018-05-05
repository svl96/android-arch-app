package com.yandex.android.mynotesandroid.data.locale

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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

    @Query("SELECT * FROM $TABLE_NAME ORDER BY ${Columns.DATE}")
    abstract fun getNotesList() : Flowable<List<Note>>

}