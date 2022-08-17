package com.windranger.reminder

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.support.multidex.MultiDexApplication
import com.windranger.reminder.di.DaggerAppComponent
import dagger.android.*
import timber.log.Timber
import javax.inject.Inject



class App : MultiDexApplication(), HasActivityInjector, HasBroadcastReceiverInjector, HasServiceInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var broadcastInjector: DispatchingAndroidInjector<BroadcastReceiver>
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        Timber.plant(Timber.DebugTree())

        createNotificationChannel()
    }

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = broadcastInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    fun hasNetwork(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }



    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Windranger"
            val description = "This is windranger channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "default_channel_id"
    }
}