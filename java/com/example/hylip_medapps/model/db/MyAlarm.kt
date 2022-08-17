package com.windranger.reminder.model.db

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class MyAlarm(
        @Id
        var id: Long = 0,

        var time: String,
        var isActive: Boolean = true,
        var isRepeat: Boolean = false,
        var day: String,
        var label: String = "Alarm"
) : Parcelable