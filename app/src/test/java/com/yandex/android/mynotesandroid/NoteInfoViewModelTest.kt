package com.yandex.android.mynotesandroid

import android.app.Application
import com.yandex.android.mynotesandroid.di.AppComponent
import com.yandex.android.mynotesandroid.domain.LoadNotesUseCase
import com.yandex.android.mynotesandroid.ui.NoteInfoViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class NoteInfoViewModelTest {

    private lateinit var mApp : App
    private lateinit var mLoadNotesUseCase : LoadNotesUseCase
    private lateinit var mNoteInfoViewModel: NoteInfoViewModel

    @Before
    fun createMockObjects() {
        mLoadNotesUseCase = Mockito.mock(LoadNotesUseCase::class.java)
        mApp = Mockito.mock(App::class.java)
        mNoteInfoViewModel = NoteInfoViewModel(mApp, mLoadNotesUseCase)

        val appComponent = Mockito.mock(AppComponent::class.java)
        Mockito.verify(appComponent, Mockito.never()).inject(mNoteInfoViewModel)

        Mockito.`when`(mApp.getAppComponent()).thenReturn(appComponent)
    }

    @Test
    fun createNote_shouldNotCallInsert_ifEmptyTitleAndContent() {
        mNoteInfoViewModel.saveData("", "")

        Mockito.verify(mLoadNotesUseCase, Mockito.never()).insertOrUpdateNotes(Mockito.anyList())
    }

    @Test
    fun loadNote_shouldNotCall_getNote() {
        mNoteInfoViewModel.loadNote("")

        Mockito.verify(mLoadNotesUseCase, Mockito.never()).getNote(Mockito.anyString())
    }

}