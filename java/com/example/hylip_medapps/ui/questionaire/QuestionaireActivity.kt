package com.windranger.reminder.ui.questionaire

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.ui.main.MainActivity
import com.windranger.reminder.util.SessionManager
import com.windranger.reminder.util.ext.gone
import com.windranger.reminder.util.ext.launchActivity
import com.windranger.reminder.util.ext.visible
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_questionaire.*
import timber.log.Timber
import javax.inject.Inject

class QuestionaireActivity : ToolbarActivity() {

    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionaire)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        btnSave.setOnClickListener { launchActivity<MainActivity>() }

        val title = intent.getStringExtra(EXTRAS_DATA)
        mTitle.text = title

        val url = if (title == QUESTIONAIRE) {
            "http://app.apoteker-reminder.xyz/kuisioner/${sessionManager.id}"
        } else "http://app.apoteker-reminder.xyz/kuisioner-app/${sessionManager.id}"

        webview.webViewClient = MyClient()
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(url)
    }

    inner class MyClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            Timber.d("URL: $url onPageStarted")
            if (url == "http://app.apoteker-reminder.xyz/kuisioner-finish") {
                launchActivity<MainActivity> {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                finish()
            } else {
                super.onPageStarted(view, url, favicon)
                progressBar.visible()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.gone()
        }

    }

    companion object {
        val QUESTIONAIRE = "Kuesioner"
        val APP_QUEST = "Kuesioner Aplikasi"
    }
}