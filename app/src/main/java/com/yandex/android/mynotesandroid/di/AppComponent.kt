package com.yandex.android.mynotesandroid.di

import com.yandex.android.mynotesandroid.ui.NotesViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface AppComponent {

    fun inject(notesViewModel: NotesViewModel)

}