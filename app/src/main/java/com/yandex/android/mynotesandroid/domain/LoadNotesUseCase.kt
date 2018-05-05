package com.yandex.android.mynotesandroid.domain

import io.reactivex.Flowable


class LoadNotesUseCase(
        private val localRepository: LocalRepository) {

    fun getNotes() : Flowable<List<Note>> {
        val data = localRepository.getNotesList()
        // here we update data from remote repository
        return data
    }
}