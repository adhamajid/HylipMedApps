package com.windranger.reminder.base.recycler

import android.support.annotation.LayoutRes
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class RvAdapter<T, VH : RVViewHolder<T>>(diffCallback: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    fun getObject(position: Int): T = getItem(position)

    protected fun getView(@LayoutRes resId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }
}