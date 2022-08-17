package com.windranger.reminder.ui.alarm

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.model.db.MyAlarm_
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.boxFor
import io.objectbox.reactive.DataSubscription
import kotlinx.android.synthetic.main.activity_alarm.*
import me.didik.ioswidget.dialog.IOSDialog
import javax.inject.Inject

class AlarmActivity : ToolbarActivity() {

    @Inject lateinit var boxStore: BoxStore

    private lateinit var alarmBox: Box<MyAlarm>

    private val adapter by lazy { AlarmAdapter(listener) }

    private lateinit var subscription: DataSubscription

    private val deleteDialog by lazy { IOSDialog(this) }
    private var selectedPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmBox = boxStore.boxFor()
        mActionBar.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { launchActivity<AddAlarmActivity>() }

        rvAlarm.layoutManager = LinearLayoutManager(this)
        rvAlarm.adapter = adapter

        initDeleteDialog()

        val query = alarmBox.query().order(MyAlarm_.time).build()
        subscription = query.subscribe()
                .on(AndroidScheduler.mainThread())
                .observer { adapter.updateList(it) }
    }

    override fun onDestroy() {
        subscription.cancel()
        super.onDestroy()
    }

    private fun initDeleteDialog() {
        deleteDialog.setTitle("Menghapus Alarm")
        deleteDialog.setSubtitle("Apakah Anda yakin ingin menghapus alarm ini?")
        deleteDialog.setNegativeLabel("Batal")
        deleteDialog.setPositiveLabel("Hapus")
        deleteDialog.setNegativeListener { it.dismiss() }
        deleteDialog.setPositiveListener {
            it.dismiss()
            alarmBox.remove(adapter.getItem(selectedPos))
        }
    }

    private val listener = object : AlarmAdapter.AlarmListener {
        override fun onActiveChange(item: MyAlarm) {
            alarmBox.put(item)
        }

        override fun onEdit(position: Int) {
            openEdit(position)
        }

        override fun onDelete(position: Int) {
            selectedPos = position
            deleteDialog.show()
        }

    }

    private fun openEdit(position: Int) {
        launchActivity<AddAlarmActivity> {
            putExtra(EXTRAS_DATA, adapter.getItem(position))
        }
    }
}