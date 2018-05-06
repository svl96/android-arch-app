package com.yandex.android.mynotesandroid.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import com.yandex.android.mynotesandroid.data.locale.NotesDao


@Entity(tableName = NotesDao.TABLE_NAME,
        primaryKeys = [NotesDao.Columns.ID])
class Note(id: String, title: String, content: String, date: Long) {

    @SerializedName("id")
    @ColumnInfo(name = NotesDao.Columns.ID)
    @NonNull
    private val mId : String = id

    @SerializedName("title")
    @ColumnInfo(name = NotesDao.Columns.TITLE)
    private val mTitle : String = title

    @SerializedName("content")
    @ColumnInfo(name = NotesDao.Columns.CONTENT)
    private val mContent : String = content

    @SerializedName("date")
    @ColumnInfo(name = NotesDao.Columns.DATE)
    private val mDate : Long = date

    fun getId() : String {
        return mId
    }

    fun getTitle() : String {
        return mTitle
    }

    fun getContent() : String {
        return mContent
    }

    fun getDate() : Long {
        return mDate
    }


}