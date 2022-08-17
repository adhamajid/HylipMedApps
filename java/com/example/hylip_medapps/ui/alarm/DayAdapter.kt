package com.windranger.reminder.ui.alarm

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVSampleAdapter
import com.windranger.reminder.model.Day
import kotlinx.android.synthetic.main.item_alarm_day.view.*

class DayAdapter : RVSampleAdapter<DayAdapter.DayVH>() {

    private var checked = SparseBooleanArray()
    private val dataset = listOf(
            Day("S", "Sen"),
            Day("S", "Sel"),
            Day("R", "Rab"),
            Day("K", "Kam"),
            Day("J", "Jum"),
            Day("S", "Sab"),
            Day("M", "Ming"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayVH {
        return DayVH(getView(R.layout.item_alarm_day, parent))
    }

    override fun onBindViewHolder(holder: DayVH, position: Int) {
        holder.bind(dataset[position].label)
    }

    override fun getItemCount(): Int = dataset.size

    fun setChecked(newChecked: SparseBooleanArray) {
        checked = newChecked
        notifyDataSetChanged()
    }

    fun getSelectedDays(): String {
        var str = ""
        (0 until 7)
                .filter { checked.get(it, false) }
                .forEach { str += if (str.isNotEmpty()) ", ${dataset[it].name}" else dataset[it].name }
        return str
    }

    inner class DayVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val isChecked = checked.get(adapterPosition, false)
                setChecked(!isChecked)
                checked.put(adapterPosition, !isChecked)
            }
        }

        fun bind(item: String) {
            itemView.tvTitle.text = item

            setChecked(checked.get(adapterPosition, false))
        }

        private fun setChecked(isChecked: Boolean) {
            itemView.apply {
                root.isActivated = isChecked
                tvTitle.isActivated = isChecked
            }
        }

    }
}