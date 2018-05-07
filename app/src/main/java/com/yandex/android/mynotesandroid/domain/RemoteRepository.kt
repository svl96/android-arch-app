package com.yandex.android.mynotesandroid.domain

import io.reactivex.Single
import retrofit2.Response


interface RemoteRepository {

    fun getNotes() : Single<Response<List<Note>>>
}