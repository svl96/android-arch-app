package com.yandex.android.mynotesandroid

import com.yandex.android.mynotesandroid.data.remote.AuthService
import com.yandex.android.mynotesandroid.data.remote.AuthServiceImpl
import com.yandex.android.mynotesandroid.data.remote.NotesService
import com.yandex.android.mynotesandroid.data.remote.RemoteRepositoryImpl
import com.yandex.android.mynotesandroid.domain.Note
import com.yandex.android.mynotesandroid.domain.RemoteRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class RemoteRepositoryImplTest {

    private lateinit var authService: AuthService
    private lateinit var notesService: NotesService
    private lateinit var remoteRepository: RemoteRepository

    @Before
    fun initMockObjects() {
        authService = Mockito.mock(AuthService::class.java)
        notesService = Mockito.mock(NotesService::class.java)
        remoteRepository = RemoteRepositoryImpl(notesService, authService)
    }

    @Test
    fun getNotes_shouldCallGetNotes() {
        val token = "Test Token"
        val header = "OAuth $token"
        Mockito.`when`(authService.getToken()).thenReturn(token)

        remoteRepository.getNotes()
        Mockito.verify(notesService).getNotes(header)
    }

    @Test
    fun getNote_shouldCallGetNote() {
        val token = "Test Token"
        val header = "OAuth $token"
        val uid = "test-id"
        Mockito.`when`(authService.getToken()).thenReturn(token)

        remoteRepository.getNote(uid)
        Mockito.verify(notesService).getNote(header, uid)
    }

    @Test
    fun postNote_shouldCallPostNote() {
        val token = "Test Token"
        val header = "OAuth $token"
        val note = Mockito.mock(Note::class.java)
        Mockito.`when`(authService.getToken()).thenReturn(token)

        remoteRepository.postNote(note)
        Mockito.verify(notesService).postNote(header, note)
    }

    @Test
    fun updateNote_shouldCallUpdateNote() {
        val token = "Test Token"
        val uid = "test-id"
        val header = "OAuth $token"
        val note = Mockito.mock(Note::class.java)
        Mockito.`when`(authService.getToken()).thenReturn(token)

        remoteRepository.updateNote(uid, note)
        Mockito.verify(notesService).updateNote(header, uid, note)
    }

    @Test
    fun deleteNote_shouldCallDeleteNote() {
        val token = "Test Token"
        val uid = "test-id"
        val header = "OAuth $token"
        Mockito.`when`(authService.getToken()).thenReturn(token)

        remoteRepository.deleteNote(uid)
        Mockito.verify(notesService).deleteNote(header, uid)
    }


    @Test
    fun getNotes_createBadHeader_ifGetTokenReturnsNull() {
        val header = "OAuth "
        Mockito.`when`(authService.getToken()).thenReturn(null)

        remoteRepository.getNotes()
        Mockito.verify(notesService).getNotes(header)
    }

}