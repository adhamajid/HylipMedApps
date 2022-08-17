package com.windranger.reminder.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.windranger.reminder.util.ext.TIME_FORMAT
import com.windranger.reminder.util.ext.getDateNow
import timber.log.Timber


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("Alarm: onReceive called")
        if (context != null && intent != null) {
            intent.putExtra("data", getDateNow(TIME_FORMAT))
            AlarmService.enqueueWork(context, intent)
        }
    }
}

fun setupAlarm(context: Context, force: Boolean) {
    Timber.d("Alarm: setupAlarm called")

    cancelAlarm(context)
    val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // EVERY X MINUTES
    val delay = (1000 * 60).toLong()
    var time = System.currentTimeMillis()
    if (!force) {
        time += delay
    }

    val pendingIntent = getPendingIntent(context)
    /* fire the broadcast */
    val SDK_INT = Build.VERSION.SDK_INT
    if (SDK_INT < Build.VERSION_CODES.KITKAT)
        alarm.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
        alarm.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    else if (SDK_INT >= Build.VERSION_CODES.M) {
        alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}

fun cancelAlarm(context: Context) {
    Timber.d("Alarm: cancelAlarm called")

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(getPendingIntent(context))
}

fun getPendingIntent(context: Context): PendingIntent {
    val CUSTOM_INTENT = "com.test.intent.action.ALARM"

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    alarmIntent.action = CUSTOM_INTENT

    return PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
}