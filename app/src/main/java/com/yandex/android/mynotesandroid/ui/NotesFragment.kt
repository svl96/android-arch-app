package com.yandex.android.mynotesandroid.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.yandex.android.mynotesandroid.R
import android.widget.Toast




class NotesFragment : Fragment() {
    companion object {
        const val TAG = "NotesFragment"

        @JvmStatic
        fun getInstance() : NotesFragment {
            return NotesFragment()
        }
    }

    private var recyclerView : RecyclerView? = null
    private var floatingButton : FloatingActionButton? = null
    private var notesRecyclerAdapter : NotesRecyclerAdapter? = null
    private var swipeRefreshLayout : SwipeRefreshLayout? = null

    private var notesViewModel : NotesViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_main, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view?.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout?.setOnRefreshListener { onUpdateNotes() }

        floatingButton = view?.findViewById(R.id.fab)
        floatingButton?.setOnClickListener { vi ->
//            Snackbar.make(vi, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
                    Log.d(TAG, "click()")
                    openNoteInfoFragment(null)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = view?.findViewById(R.id.recycler_notes)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        notesRecyclerAdapter = NotesRecyclerAdapter(object : NotesRecyclerAdapter.Callback {
            override fun onItemClick(noteId: String) {
                openNoteInfoFragment(noteId)
            }
        })
        recyclerView?.adapter = notesRecyclerAdapter

    }

    private fun openNoteInfoFragment(noteId : String?) {
        Log.d(TAG, "openNote $noteId")
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                        NoteInfoFragment.getInstance(noteId),
                        NoteInfoFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

        subscribeUi()
    }

    private fun subscribeUi() {
        notesViewModel?.getNotes()?.observe(this, Observer { notes ->
            Log.d(TAG, "Update UI")
            swipeRefreshLayout?.isRefreshing = false
            notesRecyclerAdapter?.setNotes(notes)
        })

        notesViewModel?.getShowError()?.observe(this, Observer { isError ->
            if (isError != null && isError) {
                Toast.makeText(context, "Loading Error", Toast.LENGTH_SHORT).show()
            }
        })

        notesViewModel?.getShowLoading()?.observe(this, Observer { isShowLoading ->
            swipeRefreshLayout?.isRefreshing = isShowLoading != null && isShowLoading
        })
    }

    private fun onUpdateNotes() {
        Log.d(TAG, "OnUpdateNotes")
        notesViewModel?.onUpdateNotes()
    }
}