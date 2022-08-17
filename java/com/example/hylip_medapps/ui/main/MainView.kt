package com.windranger.reminder.ui.main

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.alarm.Alarm

interface MainView : BaseView {
    fun updateList(list: List<Alarm>)
}