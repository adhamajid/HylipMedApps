package com.windranger.reminder.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.JobIntentService
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import com.windranger.reminder.App
import com.windranger.reminder.R
import com.windranger.reminder.model.db.Medicine
import com.windranger.reminder.model.db.Medicine_
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.model.db.MyAlarm_
import com.windranger.reminder.model.terapi.Terapi
import com.windranger.reminder.ui.notif.NotifActivity
import com.windranger.reminder.ui.notif.alarm.NotifAlarmActivity
import com.windranger.reminder.ui.terapi.TerapiActivity
import com.windranger.reminder.util.ext.default
import dagger.android.AndroidInjection
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class AlarmService : JobIntentService() {

    @Inject lateinit var boxStore: BoxStore

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onHandleWork(intent: Intent) {
        /* your code here */
        Timber.d("Alarm: onHandleWork called")
        val data = intent.getStringExtra("data") ?: "Kosong"
        Timber.d("Alarm: $data")

        val terapiBox: Box<Terapi> = boxStore.boxFor()
        val terapis = terapiBox.all

        if (data == "08:00" && terapis.isNotEmpty()) {
            val random = Random()
            val obj = terapis[random.nextInt(terapis.size)]
            sendNotification(obj.title.default, obj.content.default)
        }

        /* Find medicine in this time */
        val medicineBox: Box<Medicine> = boxStore.boxFor()
        val query = medicineBox.query().contains(Medicine_.times, data).build().find()

        /* Open alarm page if data not empty*/
        if (query.isNotEmpty()) {
            val notifIntent = Intent(this, NotifActivity::class.java)
            notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            notifIntent.putExtra("data", data)
            startActivity(notifIntent)
        } else {
            val alarmBox: Box<MyAlarm> = boxStore.boxFor()

            val alarmQuery = alarmBox.query()
                    .equal(MyAlarm_.isActive, true)
                    .and().contains(MyAlarm_.day, getDay())
                    .and().equal(MyAlarm_.time, data)
                    .build()
                    .findFirst()

            alarmQuery?.let {
                val notifIntent = Intent(this, NotifAlarmActivity::class.java)
                notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                notifIntent.putExtra("data", it)
                notifIntent.putExtra("time", data)
                startActivity(notifIntent)
            }

        }

        /* reset the alarm */
        setupAlarm(applicationContext, false)
        stopSelf()
    }

    private fun getDay(): String {
        val days = listOf("Ming", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
        val cal = Calendar.getInstance()
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        return days[dayOfWeek - 1]
    }

    companion object {
        fun enqueueWork(ctx: Context, intent: Intent) {
            enqueueWork(ctx, AlarmService::class.java, 1000, intent)
        }
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, TerapiActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build())
        }
    }

}

