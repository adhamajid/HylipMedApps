package com.windranger.reminder.base.recycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class RVSampleAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    override fun onBindViewHolder(holder: VH, position: Int){

    }

    override fun getItemCount(): Int = 10

    protected fun getView(@LayoutRes resId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }
}