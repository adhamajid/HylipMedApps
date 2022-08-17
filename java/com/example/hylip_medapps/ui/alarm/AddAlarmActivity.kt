package com.windranger.reminder.ui.alarm

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.SparseBooleanArray
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.util.ext.TIME_FORMAT
import com.windranger.reminder.util.ext.dateFormatter
import com.windranger.reminder.util.ext.gone
import com.windranger.reminder.util.ext.visible
import dagger.android.AndroidInjection
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_add_alarm.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AddAlarmActivity : ToolbarActivity() {

    @Inject lateinit var boxStore: BoxStore

    private val calendar by lazy { Calendar.getInstance() }
    private lateinit var timeDialog: TimePickerDialog

    private val dayAdapter by lazy { DayAdapter() }

    private lateinit var alarmStore: Box<MyAlarm>

    private var prevData: MyAlarm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        alarmStore = boxStore.boxFor()

        btnSave.setOnClickListener { submit() }

        tvClock.setOnClickListener { timeDialog.show(fragmentManager, "") }

        checkboxRepeat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) rvDay.visible()
            else rvDay.gone()
        }

        val time = dateFormatter(calendar.timeInMillis, TIME_FORMAT)
        tvClock.text = time

        initTimeDialog()
        initRecycler()

        val data = intent.getParcelableExtra<MyAlarm>(EXTRAS_DATA)
        data?.let {
            prevData = it
            initData(it)
        }
    }

    private fun initRecycler() {
        rvDay.layoutManager = GridLayoutManager(this, 7)
        rvDay.adapter = dayAdapter

        val checked = SparseBooleanArray(7)
        for (i in 0 until 7) checked.put(i, true)

        dayAdapter.setChecked(checked)

    }

    private fun initTimeDialog() {
        timeDialog = TimePickerDialog.newInstance(
                { _, hourOfDay, minute, second ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, second)

                    val time = dateFormatter(calendar.timeInMillis, TIME_FORMAT)
                    tvClock.text = time
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                true
        )
    }

    private fun getDay(): String {
        val days = listOf("Ming", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
        val cal = Calendar.getInstance()
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        return days[dayOfWeek - 1]
    }

    private fun submit() {
        val selectedDays = dayAdapter.getSelectedDays()

        val day = if (checkboxRepeat.isChecked && selectedDays.isNotEmpty()) selectedDays else getDay()
        val lblStr = etLabel.text.toString()
        val label = if (lblStr.isEmpty()) "Alarm" else lblStr

        val alarm = MyAlarm(time = tvClock.text.toString(), isActive = switchIsActive.isChecked,
                isRepeat = checkboxRepeat.isChecked, day = day, label = label)

        Timber.d("Day: $alarm")

        prevData?.let { alarm.id = it.id }

        alarmStore.put(alarm)
        onBackPressed()
    }

    private fun initData(data: MyAlarm) {
        tvClock.text = data.time
        etLabel.setText(data.label)

        switchIsActive.isChecked = data.isActive
        checkboxRepeat.isChecked = data.isRepeat

        if (data.isRepeat) {
            val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Ming")
            val checked = SparseBooleanArray()
            days.forEachIndexed { index, s -> checked.put(index, data.day.contains(s)) }
            dayAdapter.setChecked(checked)

            rvDay.visible()
        }
    }

}