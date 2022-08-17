package com.windranger.reminder.ui.desease

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.desease.Desease

interface DeseaseView : BaseView {
    fun updateList(list: List<Desease>)
    fun openQuestionairePage()
    fun showLoadingDialog()
    fun hideLoadingDialog()
}