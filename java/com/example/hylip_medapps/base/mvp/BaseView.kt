package com.windranger.reminder.base.mvp

interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}