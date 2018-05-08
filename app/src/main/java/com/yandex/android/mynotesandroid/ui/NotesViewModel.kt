package com.yandex.android.mynotesandroid.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.NonNull
import android.util.Log
import com.yandex.android.mynotesandroid.App
import com.yandex.android.mynotesandroid.domain.LoadNotesUseCase
import com.yandex.android.mynotesandroid.domain.Note
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NotesViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        const val TAG = "NotesViewModel"
    }


    var loadNotesUseCase : LoadNotesUseCase? = null
    @Inject set

    private val mNotesLiveData : MutableLiveData<List<Note>> = MutableLiveData()
    private val mShowLoading : MutableLiveData<Boolean> = MutableLiveData()
    private val mShowError : MutableLiveData<Boolean> = MutableLiveData()

    private val mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    init {
        (app as (App) ).getAppComponent().inject(this)
        mNotesLiveData.value = null
        loadNotes()
    }

    private fun loadNotes() {
        mShowLoading.postValue(true)
        if (loadNotesUseCase == null){
            mShowError.postValue(true)
            return
        }
        val disposable : Disposable = loadNotesUseCase!!.getNotes()
                .subscribe(
                        { notes : List<Note> ->
                            Log.d(TAG, "Load New Data")
                            mNotesLiveData.postValue(notes)
                            mShowError.postValue(false)
                            mShowLoading.postValue(false)
                        },
                        {throwable ->
                            Log.e(TAG, throwable.toString())
                            mShowError.postValue(true)
                            mShowLoading.postValue(false)
                            mNotesLiveData.postValue(null)
                        }
                )
        mCompositeDisposable.add(disposable)
    }

    fun getNotes() : LiveData<List<Note>> {
        return mNotesLiveData
    }

    fun getShowLoading() : LiveData<Boolean> {
        return mShowLoading
    }

    fun getShowError() : LiveData<Boolean> {
        return mShowError
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
    }

    fun onUpdateNotes() {
        Log.d(TAG, "OnUpdateNotes")
        loadNotesUseCase?.updateNotes()
    }

}