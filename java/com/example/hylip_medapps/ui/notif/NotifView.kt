package com.windranger.reminder.ui.notif

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.db.Medicine

interface NotifView : BaseView {
    fun openDashboard()
    fun setMedicine(data: Medicine)
}