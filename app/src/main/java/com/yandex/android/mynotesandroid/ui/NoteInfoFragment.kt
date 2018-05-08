package com.yandex.android.mynotesandroid.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.EditText
import com.yandex.android.mynotesandroid.R


class NoteInfoFragment : Fragment() {
    companion object {
        const val TAG = "NoteInfoFragment"
        const val NOTE_ID_KEY = "NOTE_ID"

        @JvmStatic
        fun getInstance(noteId: String?) : NoteInfoFragment {
            val args = Bundle()
            if (noteId != null)
                args.putString(NOTE_ID_KEY, noteId)
            val outFragment = NoteInfoFragment()
            outFragment.arguments = args
            return outFragment
        }
    }

    private var noteInfoViewModel : NoteInfoViewModel? = null

    private var titleView : EditText? = null
    private var contentView : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_info, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView = view?.findViewById(R.id.title_edit)
        contentView = view?.findViewById(R.id.content_edit)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        noteInfoViewModel = ViewModelProviders.of(this).get(NoteInfoViewModel::class.java)
        val noteId : String? = arguments.getString(NOTE_ID_KEY)
        if (noteId != null && noteId != "") {
            noteInfoViewModel?.loadNote(noteId)
            if (savedInstanceState == null)
                subscribeUi()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.save_button, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.save_note_action) {
            saveNote()
            fragmentManager.popBackStack()
        }
        return true
    }

    private fun saveNote() {
        noteInfoViewModel?.saveData(titleView?.text.toString(), contentView?.text.toString())
    }

    private fun subscribeUi() {
        Log.d(TAG, "subscribeUi")

        noteInfoViewModel?.getSingleLiveEvent()?.observe(this, Observer { note ->
            titleView?.setText(note?.getTitle())
            contentView?.setText(note?.getContent())
        })

    }



}