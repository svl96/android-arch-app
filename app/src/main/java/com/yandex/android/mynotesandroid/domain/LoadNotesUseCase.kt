package com.yandex.android.mynotesandroid.domain

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class LoadNotesUseCase(val localRepository: LocalRepository) {

    fun getNotes() : Flowable<List<Note>> {
        val data = localRepository.getNotesList()
        // here we update data from remote repository
        updateNotes()
        return data
    }

    fun updateNotes() {
        return
    }

    fun loadNote(noteId: String) : Flowable<List<Note>> {
        val data = localRepository.getNote(noteId)
        // here we do something, or don't
        return data
    }

    fun insertOrUpdateNotes(notes : List<Note>){
        Schedulers.io().createWorker().schedule {
            localRepository.insertOrUpdate(notes)
        }
    }
}