package com.windranger.reminder.util

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.base.recycler.RVSampleAdapter

class SimpleRVAdapter(@LayoutRes private val layoutId: Int) : RVSampleAdapter<SimpleRVAdapter.SimpleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleVH {
        return SimpleVH(getView(layoutId, parent))
    }

    inner class SimpleVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}