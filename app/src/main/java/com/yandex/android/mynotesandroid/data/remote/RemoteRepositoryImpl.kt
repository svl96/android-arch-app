package com.yandex.android.mynotesandroid.data.remote

import com.yandex.android.mynotesandroid.domain.Note
import com.yandex.android.mynotesandroid.domain.RemoteRepository
import io.reactivex.Single
import retrofit2.Response


class RemoteRepositoryImpl(private val notesService: NotesService,
                           private val authService: AuthService)
    : RemoteRepository {

    private fun getAuthHeader() : String {
        val token = authService.getToken() ?: ""
        return "OAuth $token"
    }

    override fun getNotes(): Single<Response<List<Note>>> {
        val header = getAuthHeader()
        return notesService.getNotes(header)
    }

    override fun getNote(uid: String): Single<Response<Note>> {
        return notesService.getNote(getAuthHeader(), uid)
    }

    override fun postNote(note: Note): Single<Response<Note>> {
        return notesService.postNote(getAuthHeader(), note)
    }

    override fun updateNote(uid: String, note: Note): Single<Response<Note>> {
        return notesService.updateNote(getAuthHeader(), uid, note)
    }

    override fun deleteNote(uid: String): Single<Response<Note>> {
        return notesService.deleteNote(getAuthHeader(), uid)
    }
}