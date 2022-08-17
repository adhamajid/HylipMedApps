package com.windranger.reminder.ui.main

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.windranger.reminder.R
import com.windranger.reminder.ui.auth.login.LoginActivity
import com.windranger.reminder.util.GlideApp
import com.windranger.reminder.util.SessionManager
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlideApp.with(this)
                .load(R.drawable.logo)
                .dontAnimate()
                .into(ivLogo)

        Handler().postDelayed({
            if (sessionManager.isLoggedIn) launchActivity<MainActivity>()
            else launchActivity<LoginActivity>()
            finish()
        }, 1000)
    }
}
