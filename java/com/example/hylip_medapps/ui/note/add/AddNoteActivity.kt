package com.windranger.reminder.ui.note.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.note.Note
import com.windranger.reminder.util.ext.default
import com.windranger.reminder.util.ext.isEmpty
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_add_note.*
import javax.inject.Inject

class AddNoteActivity : ToolbarActivity(), AddNoteView {

    @Inject lateinit var presenter: AddNotePresenter

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        btnSave.setOnClickListener { submit() }

        presenter.attachView(this)

        val data = intent.getParcelableExtra<Note>(EXTRAS_DATA)
        data?.let {
            id = it.id.default
            invalidateOptionsMenu()
            initData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (id != 0) {
            menuInflater.inflate(R.menu.menu_add_note, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            presenter.delete(id)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun closePage() {
        setOk()
    }

    override fun showLoading() {
        showLoadingBar()
    }

    override fun hideLoading() {
        hideLoadingBar()
    }

    override fun showMessage(message: String) {
        showPopup(message)
    }

    private fun initData(data: Note) {
        etTitle.setText(data.title)
        etContent.setText(data.content)
    }

    private fun submit() {
        if (etTitle.isEmpty()) return
        if (etContent.isEmpty()) return

        val title = etTitle.text.toString()
        val content = etContent.text.toString()

        if (id == 0) presenter.add(title, content)
        else presenter.edit(id, title, content)
    }
}