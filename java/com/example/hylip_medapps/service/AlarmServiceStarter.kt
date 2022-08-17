package com.windranger.reminder.service

import android.content.Context
import android.content.Intent
import com.windranger.reminder.util.SessionManager
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class AlarmServiceStarter : DaggerBroadcastReceiver() {

    @Inject lateinit var sessionManager: SessionManager

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (sessionManager.isLoggedIn) {
            context?.let { setupAlarm(context, true) }
        }
    }
}