package com.windranger.reminder.base.mvp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.windranger.reminder.R
import me.didik.ioswidget.dialog.IOSDialog
import me.didik.ioswidget.loading.IOSLoading

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var context: Context
    protected lateinit var loadingBar: IOSLoading
    protected lateinit var dialog: IOSDialog
    protected var isAnimEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        context = this

        loadingBar = IOSLoading(this, "Loading")
        loadingBar.setCancelable(false)
        loadingBar.setCanceledOnTouchOutside(false)

        dialog = IOSDialog(this)
        dialog.apply {
            setTitle("Message")
            setPositiveLabel("Dismiss")
            setPositiveListener { dismiss() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isAnimEnabled || Build.VERSION.SDK_INT < 21) {
            overridePendingTransition(R.anim.scale_in, R.anim.slide_out_to_right)
        }
    }

    protected fun backWithFadeAnim() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    protected fun showLoadingBar() {
        loadingBar.show()
    }

    protected fun hideLoadingBar() {
        loadingBar.dismiss()
    }

    protected fun showPopup(message: String) {
        dialog.setSubtitle(message)
        dialog.show()
    }

    protected fun setOk(intent: Intent = Intent()) {
        setResult(Activity.RESULT_OK, intent)
        finish()
        overridePendingTransition(R.anim.scale_in, R.anim.slide_out_to_right)
    }

    companion object {
        const val EXTRAS_DATA = "data"
    }
}