package com.windranger.reminder.base.mvp

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.windranger.reminder.R

abstract class ToolbarActivity : BaseActivity() {
    protected lateinit var mActionBar: ActionBar
    protected lateinit var mTitle: TextView

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.activity_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)

        super.setContentView(fullView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        mTitle = toolbar.findViewById(R.id.toolbar_title)


        setSupportActionBar(toolbar)
        mActionBar = supportActionBar!!
        mActionBar.title = ""
        //mActionBar.elevation = 1F

        var activityInfo: ActivityInfo? = null
        try {
            activityInfo = packageManager.getActivityInfo(
                    componentName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        activityInfo?.let {
            val title = activityInfo.loadLabel(packageManager)
                    .toString()
            mTitle.text = title
        }
    }
}