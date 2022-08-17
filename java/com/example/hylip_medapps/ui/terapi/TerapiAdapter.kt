package com.windranger.reminder.ui.terapi

import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVViewHolder
import com.windranger.reminder.base.recycler.RvAdapter
import com.windranger.reminder.model.terapi.Terapi
import kotlinx.android.synthetic.main.item_terapi.view.*

class TerapiAdapter : RvAdapter<Terapi, TerapiAdapter.TerapiVH>(TerapiDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerapiVH {
        return TerapiVH(getView(R.layout.item_terapi, parent))
    }

    inner class TerapiVH(itemView: View) : RVViewHolder<Terapi>(itemView) {
        override fun bind(item: Terapi) {
            itemView.apply {
                tvTitle.text = item.title
                tvDesc.text = item.content
            }
        }
    }

    class TerapiDiff : DiffUtil.ItemCallback<Terapi>() {
        override fun areItemsTheSame(oldItem: Terapi?, newItem: Terapi?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Terapi?, newItem: Terapi?): Boolean {
            return oldItem?.title == newItem?.title
        }

    }
}