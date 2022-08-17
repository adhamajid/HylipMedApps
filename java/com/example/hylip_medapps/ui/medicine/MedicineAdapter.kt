package com.windranger.reminder.ui.medicine

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.model.alarm.Alarm
import kotlinx.android.synthetic.main.item_medicine.view.*


class MedicineAdapter(private val listener: MedicineListener)
    : ListAdapter<Alarm, MedicineAdapter.MedicineVH>(AlaramDiff()) {

    override fun onBindViewHolder(holder: MedicineVH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_medicine, parent, false)
        val holder = MedicineVH(view)

        holder.itemView.btnMore.setOnClickListener {
            val popup = PopupMenu(parent.context, it)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.menu_medicine_overflow, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> listener.onEdit(holder.adapterPosition)
                    R.id.action_delete -> listener.onDelete(holder.adapterPosition)
                }
                true
            }
            popup.show()
        }

        return holder
    }

    fun getObject(position: Int): Alarm = getItem(position)

    inner class MedicineVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Alarm) {
            itemView.apply {
                val name = if (item.otherName.isNullOrEmpty()) item.name else "${item.name} / ${item.otherName}"
                tvTitle.text = name
                tvConsume.text = item.consumed
                var timeStr = ""
                item.times?.forEachIndexed { index, times ->
                    timeStr += if (index == 0) times.time else " | ${times.time}"
                }
                tvTime.text = timeStr
            }
        }

    }

    class AlaramDiff : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm?, newItem: Alarm?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Alarm?, newItem: Alarm?): Boolean {
            return false
        }

    }

    interface MedicineListener {
        fun onEdit(position: Int)
        fun onDelete(position: Int)
    }
}