package com.yandex.android.mynotesandroid.data.locale

import com.yandex.android.mynotesandroid.domain.LocalRepository
import com.yandex.android.mynotesandroid.domain.Note
import io.reactivex.Flowable


class LocalRepositoryImpl(private val notesDao: NotesDao) : LocalRepository {

    override fun getNote(noteId: String): Flowable<List<Note>> {
        return notesDao.getNote(noteId)
    }

    override fun getNotesList(): Flowable<List<Note>> {
        return notesDao.getNotesList()
    }

    override fun insertOrUpdate(notes: List<Note>) {
        notesDao.insertOrUpdate(notes)
    }

    override fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

}