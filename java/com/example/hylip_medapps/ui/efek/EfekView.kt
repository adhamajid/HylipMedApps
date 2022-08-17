package com.windranger.reminder.ui.efek

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.efek.Efek

interface EfekView : BaseView {
    fun updateList(list: List<Efek>)
}