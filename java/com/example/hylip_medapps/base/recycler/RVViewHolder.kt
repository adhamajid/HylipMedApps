package com.windranger.reminder.base.recycler

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class RVViewHolder<in T>(itemView: View, listener: RVListener = {})
    : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { onItemClicked(listener) }
    }

    protected open fun onItemClicked(listener: RVListener) {
        listener(adapterPosition)
    }

    abstract fun bind(item: T)
}