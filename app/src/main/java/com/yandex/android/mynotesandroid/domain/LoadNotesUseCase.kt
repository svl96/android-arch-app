package com.yandex.android.mynotesandroid.domain

import android.util.Log
import com.yandex.android.mynotesandroid.ui.NotesViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoadNotesUseCase(private val localRepository: LocalRepository,
                       private val remoteRepository: RemoteRepository) {

    // region LocalRepo

    fun getNotes() : Flowable<List<Note>> {
        val data = localRepository.getNotesList()
        // here we update data from remote repository
       // updateNotes()
        return data
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

    // endregion

    // region RemoteRepo

    fun updateNotes() {
        remoteRepository.getNotes()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {response ->
                            Log.d("Use Case", "response")
                            if (response.isSuccessful) {
                                val notes : List<Note> = response.body() ?: listOf()
                                if (notes.isNotEmpty()) {
                                    localRepository.insertOrUpdate(notes)
                                }
                            }
                        },
                        {
                            throwable -> Log.e(NotesViewModel.TAG, "updateRepos()", throwable)
                        }
                )
    }

    fun getRemoteNotes() : Single<Response<List<Note>>> {
        return remoteRepository.getNotes()
    }

    fun getRemoteNote(uid: String) : Single<Response<Note>> {
        return remoteRepository.getNote(uid)
    }

    fun postRemoteNote(note : Note) : Single<Response<Note>> {
        return remoteRepository.postNote(note)
    }

    fun updateRemoteNote(uid: String, note : Note) : Single<Response<Note>> {
        return remoteRepository.updateNote(uid, note)
    }

    fun deleteRemoteNote(uid: String) : Single<Response<Note>> {
        return remoteRepository.deleteNote(uid)
    }



    // endregion
}