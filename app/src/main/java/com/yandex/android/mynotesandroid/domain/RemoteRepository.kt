package com.yandex.android.mynotesandroid.domain

import io.reactivex.Single
import retrofit2.Response


interface RemoteRepository {

    fun getNotes() : Single<Response<List<Note>>>

    fun getNote(uid: String) : Single<Response<Note>>

    fun postNote(note: Note) : Single<Response<Note>>

    fun updateNote(uid: String, note : Note) : Single<Response<Note>>

    fun deleteNote(uid: String) : Single<Response<Note>>

}