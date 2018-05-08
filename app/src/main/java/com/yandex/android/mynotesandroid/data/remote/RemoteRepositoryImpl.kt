package com.yandex.android.mynotesandroid.data.remote

import android.util.Log
import com.yandex.android.mynotesandroid.domain.Note
import com.yandex.android.mynotesandroid.domain.RemoteRepository
import com.yandex.android.mynotesandroid.service.AuthService
import io.reactivex.Single
import retrofit2.Response


class RemoteRepositoryImpl(private val notesService: NotesService,
                           private val authService: AuthService)
    : RemoteRepository {

    override fun getNotes(): Single<Response<List<Note>>> {
        val token = authService.getToken() ?: "bad_token"
        val header = "OAuth $token"
        Log.d("RemoteRepository", header)
        return notesService.getNotes(header)
    }

}