package com.windranger.reminder.ui.terapi

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.terapi.Terapi

interface TerapiView : BaseView {
    fun updateList(list: List<Terapi>)
}