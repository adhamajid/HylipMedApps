package com.windranger.reminder.ui.notif.alarm

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.BaseActivity
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.ui.main.MainActivity
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_notif_alarm.*
import javax.inject.Inject

class NotifAlarmActivity : BaseActivity() {

    @Inject lateinit var boxStore: BoxStore

    private lateinit var alarmBox: Box<MyAlarm>
    private lateinit var data: MyAlarm

    private val mediaPlayer by lazy { MediaPlayer.create(this, R.raw.tone) }

    // Get instance of Vibrator from current Context
    private val vibrator by lazy { getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_alarm)

        alarmBox = boxStore.boxFor()

        btnDismiss.setOnClickListener {
            if (!data.isRepeat) {
                data.isActive = false
                alarmBox.put(data)
            }

            stopAudioAndVibrate()

            launchActivity<MainActivity> {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            finish()
        }

        playSoundAndVibrate()

        data = intent.getParcelableExtra(EXTRAS_DATA)
        val time = intent.getStringExtra("time")
        initData(time)
    }

    override fun onDestroy() {
        stopAudioAndVibrate()
        super.onDestroy()
    }

    private fun initData(time: String) {
        tvLabel.text = data.label
        tvTime.text = time
    }

    private fun stopAudioAndVibrate() {
        mediaPlayer.stop()
        vibrator.cancel()
    }

    private fun playSoundAndVibrate() {
        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        am.setStreamVolume(AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0)

        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // Start without a delay
        // Vibrate for 100 milliseconds
        // Sleep for 1000 milliseconds
        val pattern = longArrayOf(0, 2000, 1000)

        // The '0' here means to repeat indefinitely
        // '0' is actually the index at which the pattern keeps repeating from (the start)
        // To repeat the pattern from any other point, you could increase the index, e.g. '1'

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            //deprecated in API 26
            vibrator.vibrate(pattern, 0)
        }
    }
}