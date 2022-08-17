package com.windranger.reminder.ui.note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.note.Note
import com.windranger.reminder.ui.note.add.AddNoteActivity
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_note.*
import javax.inject.Inject

class NoteActivity : ToolbarActivity(), NoteView {

    @Inject lateinit var presenter: NotePresenter

    private val adapter by lazy { NoteAdapter { openDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { launchActivity<AddNoteActivity>(100) }

        rvNote.layoutManager = LinearLayoutManager(this)
        rvNote.adapter = adapter

        presenter.attachView(this)

        swipeRefresh.setOnRefreshListener { presenter.getNotes() }
        swipeRefresh.post { presenter.getNotes() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            swipeRefresh.post { presenter.getNotes() }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun updateList(list: List<Note>) {
        adapter.submitList(list)
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showMessage(message: String) {
        showPopup(message)
    }

    private fun openDetail(position: Int) {
        launchActivity<AddNoteActivity>(100) {
            putExtra(EXTRAS_DATA, adapter.getObject(position))
        }
    }
}