package com.yandex.android.mynotesandroid.domain

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class LoadNotesUseCase(private val localRepository: LocalRepository,
                       private val remoteRepository: RemoteRepository) {

    fun getNotes() : Flowable<List<Note>> {
        val data = localRepository.getNotesList()
        // here we update data from remote repository
        // updateNotes()
        return data
    }

    fun updateNotes() : Single<Response<List<Note>>> {
        return remoteRepository.getNotes()
    }

    fun getNote(noteId: String) : Flowable<List<Note>> {
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