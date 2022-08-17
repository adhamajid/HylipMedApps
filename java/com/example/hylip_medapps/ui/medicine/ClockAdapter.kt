package com.windranger.reminder.ui.medicine

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVListener
import com.windranger.reminder.base.recycler.RVSampleAdapter
import kotlinx.android.synthetic.main.item_reminder_clock.view.*

class ClockAdapter(private val listener: RVListener) : RVSampleAdapter<ClockAdapter.ClockVH>() {

    private var dataset = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockVH {
        return ClockVH(getView(R.layout.item_reminder_clock, parent))
    }

    override fun onBindViewHolder(holder: ClockVH, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    fun updateList(list: List<String>) {
        dataset = list.toMutableList()
        notifyDataSetChanged()
    }

    fun updateItem(item: String, position: Int) {
        dataset[position] = item
        notifyItemChanged(position)
    }

    fun getAllItem() = dataset

    inner class ClockVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { listener(adapterPosition) }
        }

        fun bind(item: String) {
            itemView.tvTitle.text = item
        }
    }
}