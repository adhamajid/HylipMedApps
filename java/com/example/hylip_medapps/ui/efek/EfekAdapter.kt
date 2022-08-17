package com.windranger.reminder.ui.efek

import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVListener
import com.windranger.reminder.base.recycler.RVViewHolder
import com.windranger.reminder.base.recycler.RvAdapter
import com.windranger.reminder.model.efek.Efek
import kotlinx.android.synthetic.main.item_efek.view.*

class EfekAdapter(private val listener: RVListener) : RvAdapter<Efek, EfekAdapter.EfekVH>(EfekDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EfekVH {
        return EfekVH(getView(R.layout.item_efek, parent))
    }

    inner class EfekVH(itemView: View) : RVViewHolder<Efek>(itemView, listener) {
        override fun bind(item: Efek) {
            itemView.apply {
                tvTitle.text = "Mekanisme Kerja ${item.name}"
                tvDesc.text = item.description
            }
        }

    }

    class EfekDiff : DiffUtil.ItemCallback<Efek>() {
        override fun areItemsTheSame(oldItem: Efek?, newItem: Efek?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Efek?, newItem: Efek?): Boolean {
            return oldItem?.name == newItem?.name
        }

    }
}