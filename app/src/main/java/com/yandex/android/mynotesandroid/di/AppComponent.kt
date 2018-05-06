package com.yandex.android.mynotesandroid.di

import com.yandex.android.mynotesandroid.ui.NoteInfoViewModel
import com.yandex.android.mynotesandroid.ui.NotesViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(notesViewModel: NotesViewModel)

    fun inject(noteInfoViewModel: NoteInfoViewModel)
}