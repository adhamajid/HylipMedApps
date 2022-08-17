package com.windranger.reminder.model.db

import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.model.alarm.Times
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Medicine(
        @Id(assignable = true)
        var id: Long = 0,
        var drugTypeId: Int,
        var drugId: Int,
        var name: String,
        var otherName: String,
        var qty: Int,
        var consumed: String,
        var consumedTime: String,
        var dosis: String,
        var times: String
) {
    fun toAlarm(): Alarm {
        val timeStr = times.split(",")
        val list = mutableListOf<Times>()
        timeStr.mapTo(list) { Times(time = it) }

        return Alarm(id = id.toInt(), drugTypeId = drugTypeId.toString(), drugId = drugId.toString(),
                name = name, otherName = otherName, qty = qty.toString(), consumed = consumed,
                consumedTime = consumedTime, dosage = dosis, times = list)
    }
}

const val CONSUMED_BEFORE = "Sebelum Makan"
const val CONSUMED_AFTER = "Sesudah Makan"
const val CONSUMED_WITH = "Bersamaan Dengan Makan"