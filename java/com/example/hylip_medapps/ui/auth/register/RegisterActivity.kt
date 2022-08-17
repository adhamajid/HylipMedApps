package com.windranger.reminder.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.BaseActivity
import com.windranger.reminder.service.setupAlarm
import com.windranger.reminder.ui.desease.DeseaseActivity
import com.windranger.reminder.util.ext.isEmailInvalid
import com.windranger.reminder.util.ext.isEmpty
import com.windranger.reminder.util.ext.isPasswordInvalid
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterView {

    @Inject lateinit var presenter: RegisterPresenter

    private var gender = "male"

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener { submitRegister() }

        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun openDashboard() {
        setupAlarm(applicationContext, true)
        launchActivity<DeseaseActivity> {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        finish()
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

    fun onGenderSelected(view: View) {
        gender = when (view.id) {
            R.id.radio_male -> "male"
            else -> "female"
        }
    }

    private fun submitRegister() {
        if (etName.isEmpty()) return
        if (etEmail.isEmailInvalid()) return
        if (etPass.isPasswordInvalid()) return

        presenter.register(etEmail.text.toString(), etName.text.toString(), etPass.text.toString(),
                gender)
    }
}