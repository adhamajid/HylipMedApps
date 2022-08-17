package com.windranger.reminder.ui.auth.login

import android.content.Intent
import android.os.Bundle
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.BaseActivity
import com.windranger.reminder.helper.SignInProvider
import com.windranger.reminder.service.setupAlarm
import com.windranger.reminder.ui.auth.register.RegisterActivity
import com.windranger.reminder.ui.main.MainActivity
import com.windranger.reminder.util.ext.isEmailInvalid
import com.windranger.reminder.util.ext.isPasswordInvalid
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject lateinit var presenter: LoginPresenter

    private lateinit var signInProvider: SignInProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInProvider = SignInProvider(this)

        btnRegister.setOnClickListener { launchActivity<RegisterActivity>() }

        btnLogin.setOnClickListener { submitLogin() }

        btnGoogle.setOnClickListener {
            signInProvider.signOut()
            signInProvider.signIn()
        }

        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let { it ->
            signInProvider.handleActivityResult(requestCode, it,
                    onSuccess = {
                        presenter.loginWithGoogle(it.email, it.name, it.id)
                    },
                    onFailed = {
                        //showPopup("Something went wrong")
                    })
        }
    }

    override fun openDashboard() {
        setupAlarm(applicationContext, true)
        launchActivity<MainActivity>()
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

    private fun submitLogin() {
        if (etEmail.isEmailInvalid()) return
        if (etPass.isPasswordInvalid()) return

        presenter.login(etEmail.text.toString(), etPass.text.toString())
    }
}