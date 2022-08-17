package com.windranger.reminder.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVSampleAdapter

class ReminderAdapter : RVSampleAdapter<ReminderAdapter.ReminderVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderVH {
        return ReminderVH(getView(R.layout.item_reminder_today, parent))
    }

    inner class ReminderVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}