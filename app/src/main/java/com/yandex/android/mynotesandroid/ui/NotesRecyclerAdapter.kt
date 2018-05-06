package com.yandex.android.mynotesandroid.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yandex.android.mynotesandroid.R
import com.yandex.android.mynotesandroid.domain.Note
import java.text.SimpleDateFormat
import java.util.*


class NotesRecyclerAdapter(val callback: Callback) : RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder>() {

    private var mNotes : List<Note>? = null

    interface Callback {
        fun onItemClick(noteId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        val view = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view, callback)
    }

    override fun getItemCount(): Int {
        return mNotes?.size ?: 0
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        val dateFormat = SimpleDateFormat("HH:mm dd MMM yy", Locale.getDefault())
        val note : Note = getItem(position)!!

        holder?.id = note.getId()
        holder?.title?.text = note.getTitle()
        holder?.content?.text = note.getContent()
        holder?.date?.text = dateFormat.format(Date(note.getDate()))
    }

    fun getItem(position: Int) : Note? {
        return mNotes?.get(position)
    }

    fun setNotes(notes : List<Note>?) {

        when {
            notes == null -> {
                notifyItemRangeRemoved(0, itemCount)
                mNotes = null
            }
            mNotes == null -> {
                mNotes = notes
                notifyItemRangeInserted(0, notes.size)
            }
            else -> {
                val oldNotes = mNotes!!
                val res : DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                    override fun getOldListSize(): Int {
                        return oldNotes.size
                    }

                    override fun getNewListSize(): Int {
                        return notes.size
                    }

                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return oldNotes[oldItemPosition].getId() ==
                                notes[newItemPosition].getId()
                    }

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        val oldNote = oldNotes[oldItemPosition]
                        val newNote = notes[newItemPosition]
                        return newNote.getId() == oldNote.getId() &&
                                TextUtils.equals(newNote.getTitle(), oldNote.getTitle()) &&
                                TextUtils.equals(newNote.getContent(), oldNote.getContent())
                    }

                })
                mNotes = notes
                res.dispatchUpdatesTo(this)
            }
        }
    }


    class NoteViewHolder(itemView: View?, private val callback: NotesRecyclerAdapter.Callback)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {


        var id : String = ""
        val title: TextView? = itemView?.findViewById(R.id.item_title)
        val content: TextView? = itemView?.findViewById(R.id.item_content)
        val date: TextView? = itemView?.findViewById(R.id.item_date)

        init {
            itemView?.setOnClickListener { v -> onClick(v) }
            itemView?.setOnLongClickListener { v -> onLongClick(v) }
        }

        override fun onClick(p0: View?) {
            Log.d("Adapter", "onClick")
            callback.onItemClick(id)
        }

        override fun onLongClick(p0: View?): Boolean {
            Log.d("Adapter", "onLongClick")
            return true
        }
    }
}