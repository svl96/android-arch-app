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
import java.util.*
import javax.inject.Inject


class NoteInfoViewModel: AndroidViewModel {
    companion object {
        const val TAG = "NoteInfoViewModel"
    }

    constructor(app: Application) : super(app) {
        (app as App).getAppComponent().inject(this)
    }

    constructor(app: Application, useCase: LoadNotesUseCase)  : super(app) {
        loadNotesUseCase = useCase
    }

    var loadNotesUseCase : LoadNotesUseCase? = null
    @Inject set

    init {

    }

    private val mShowError : MutableLiveData<Boolean> = MutableLiveData()
    private val mNoteInfoLiveData : SingleLiveEvent<Note> = SingleLiveEvent()

    private val mCompositeDisposable = CompositeDisposable()



    fun loadNote(noteId: String?) {
        if (noteId.isNullOrEmpty())
            return

        if (loadNotesUseCase == null) {
            mShowError.postValue(true)
            return
        }
        val disposable: Disposable = loadNotesUseCase!!.getNote(noteId!!)
                .subscribe(
                        { notes ->
                            if (notes.isNotEmpty()) {
                                val note = notes[0]
                                mNoteInfoLiveData.postValue(note)
                                mNoteInfoLiveData.postValue(note)
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

    private fun createNote(title: String, content: String) : Note? {
        if (title == "" && content == "") {
            return null
        }
        val date = Calendar.getInstance().time.time
        val noteId = UUID.randomUUID().toString()

        return Note(noteId, title, content, date)
    }

    private fun editNote(newTitle: String, newContent: String) : Note? {
        val oldTitle = mNoteInfoLiveData.value?.getTitle()
        val oldContent = mNoteInfoLiveData.value?.getContent()
        val noteId = mNoteInfoLiveData.value!!.getId()
        val date = mNoteInfoLiveData.value!!.getDate()
        if (TextUtils.equals(newTitle, oldTitle) && TextUtils.equals(newContent, oldContent)) {
            return null
        }

        return Note(noteId, newTitle, newContent, date)
    }

    fun saveData(title: String, content: String) {
        val note = if (mNoteInfoLiveData.value == null) {
            createNote(title, content)
        } else {
            editNote(title, content)
        }

        if (note != null) {
            loadNotesUseCase?.insertOrUpdateNotes(listOf(note))
        }
    }

    fun getSingleLiveEvent() : LiveData<Note> {
        return mNoteInfoLiveData
    }

    fun getShowError() : LiveData<Boolean> {
        return mShowError
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
    }

}