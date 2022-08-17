package com.windranger.reminder.ui.alarm

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVSampleAdapter
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.ui.medicine.MedicineAdapter
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmAdapter(private val listener: AlarmListener) : RVSampleAdapter<AlarmAdapter.AlarmVH>() {
    private var dataset = mutableListOf<MyAlarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmVH {
        val holder = AlarmVH(getView(R.layout.item_alarm, parent))

        holder.itemView.switchIsActive.setOnCheckedChangeListener { _, isChecked ->
            val data = dataset[holder.adapterPosition]
            data.isActive = isChecked
            dataset[holder.adapterPosition] = data

            listener.onActiveChange(data)
        }

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

    override fun onBindViewHolder(holder: AlarmVH, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    fun getItem(position: Int) = dataset[position]

    fun remove(position: Int) {
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateList(list: List<MyAlarm>) {
        if (list.size != dataset.size || !dataset.containsAll(list)) {
            dataset = list.toMutableList()
            notifyDataSetChanged()
        }
    }

    inner class AlarmVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MyAlarm) {
            itemView.apply {
                tvLabel.text = item.label
                tvTime.text = item.time
                switchIsActive.isChecked = item.isActive
                tvDay.text = item.day
            }
        }
    }

    interface AlarmListener : MedicineAdapter.MedicineListener {
        fun onActiveChange(item: MyAlarm)
    }
}