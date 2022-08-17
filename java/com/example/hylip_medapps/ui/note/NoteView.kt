package com.windranger.reminder.ui.note

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.note.Note

interface NoteView : BaseView {
    fun updateList(list: List<Note>)
}