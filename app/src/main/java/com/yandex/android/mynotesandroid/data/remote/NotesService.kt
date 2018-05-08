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
    fun getNote(@Header("Authorization") authorization: String,
                @Path("uid") uid: String) : Single<Response<Note>>

    @POST("/notes")
    fun postNote(@Header("Authorization") authorization: String,
                 @Body note: Note) : Single<Response<Note>>

    @PUT("notes/{uid}")
    fun updateNote(@Header("Authorization") authorization: String,
                   @Path("uid") uid: String, @Body note: Note) : Single<Response<Note>>

    @DELETE("notes/{uid}")
    fun deleteNote(@Header("Authorization") authorization: String,
                   @Path("uid") uid: String) : Single<Response<Note>>

}