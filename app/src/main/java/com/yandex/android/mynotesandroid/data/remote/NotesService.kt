package com.yandex.android.mynotesandroid.data.remote

import com.yandex.android.mynotesandroid.domain.Note
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


interface NotesService {
    companion object {
        const val BASE_URL : String ="http://notes.mrdekk.ru/"
    }

    @GET("/notes")
    fun getNotes(@Header("Authorization") authorization: String) : Single<Response<List<Note>>>

    @GET("/notes/{uid}")
    fun getNote(@Path("uid") id: String) : Single<Response<Note>>

    @POST("/notes")
    fun postNote( @Body note: Note)

    @PUT("notes/{uid}")
    fun updateNote(@Path("uid") id: String, @Body note: Note)

    @DELETE("notes/{uid}")
    fun deleteNote(@Path("uid") id: String)

}