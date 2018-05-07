package com.yandex.android.mynotesandroid.data.remote

import com.yandex.android.mynotesandroid.domain.Note
import com.yandex.android.mynotesandroid.domain.RemoteRepository
import io.reactivex.Single
import retrofit2.Response


class RemoteRepositoryImpl(private val notesService: NotesService)
    : RemoteRepository {

    override fun getNotes(): Single<Response<List<Note>>> {
        return notesService.getNotes()
    }

}