package com.yandex.android.mynotesandroid.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import android.util.Log
import com.yandex.android.mynotesandroid.App
import com.yandex.android.mynotesandroid.domain.LoadNotesUseCase
import com.yandex.android.mynotesandroid.domain.Note
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class NoteInfoViewModel(app: Application) : AndroidViewModel(app) {
    companion object {
        const val TAG = "NoteInfoViewModel"
    }


    var loadNotesUseCase : LoadNotesUseCase? = null
    @Inject set

    private val mNoteInfoLiveData : MutableLiveData<Note> = MutableLiveData()
    private val mTitleLiveData : MutableLiveData<String> = MutableLiveData()
    private val mContentLiveData : MutableLiveData<String> = MutableLiveData()
    private val mShowError : MutableLiveData<Boolean> = MutableLiveData()

    private val mCompositeDisposable = CompositeDisposable()

    init {
        (app as App).getAppComponent().inject(this)
        mNoteInfoLiveData.value = null
    }

    fun loadNote(noteId: String) {
        if (loadNotesUseCase == null) {
            mShowError.postValue(true)
            return
        }
        val disposable: Disposable = loadNotesUseCase!!.loadNote(noteId)
                .subscribe(
                        { notes ->
                            if (notes.isNotEmpty()) {
                                val note = notes[0]
                                mNoteInfoLiveData.postValue(note)
                                mTitleLiveData.postValue(note.getTitle())
                                mContentLiveData.postValue(note.getContent())
                                mShowError.postValue(false)
                            } else {
                                mShowError.postValue(true)
                                mNoteInfoLiveData.postValue(null)
                            }
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message)
                            mShowError.postValue(true)
                            mNoteInfoLiveData.postValue(null)
                        }
                )
        mCompositeDisposable.add(disposable)
    }

    private fun createNote() : Note? {
        val title = mTitleLiveData.value ?: ""
        val content = mContentLiveData.value ?: ""
        if (title == "" && content == "") {
            return null
        }
        val date = Calendar.getInstance().time.time
        val noteId = UUID.randomUUID().toString()

        return Note(noteId, title, content, date)
    }

    private fun editNote() : Note? {
        val newTitle = mTitleLiveData.value ?: ""
        val newContent = mContentLiveData.value ?: ""
        val oldTitle = mNoteInfoLiveData.value?.getTitle()
        val oldContent = mNoteInfoLiveData.value?.getContent()
        val noteId = mNoteInfoLiveData.value!!.getId()
        val date = mNoteInfoLiveData.value!!.getDate()
        if (TextUtils.equals(newTitle, oldTitle) && TextUtils.equals(newContent, oldContent)) {
            return null
        }

        return Note(noteId, newTitle, newContent, date)
    }

    fun onSave() {
        val note = if (mNoteInfoLiveData.value == null) {
            createNote()
        } else { editNote() }
        if (note != null) {
            loadNotesUseCase?.insertOrUpdateNotes(listOf(note))
        }
    }

    fun getTitle(): MutableLiveData<String> {
        return mTitleLiveData
    }

    fun getContent() : MutableLiveData<String> {
        return mContentLiveData
    }

    fun getShowError() : LiveData<Boolean> {
        return mShowError
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
    }

}