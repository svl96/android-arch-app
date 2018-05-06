package com.yandex.android.mynotesandroid.domain

import io.reactivex.Flowable


interface LocalRepository {

    fun getNotesList() : Flowable<List<Note>>

    fun insertOrUpdate(notes: List<Note>)

    fun getNote(noteId: String) : Flowable<List<Note>>
}