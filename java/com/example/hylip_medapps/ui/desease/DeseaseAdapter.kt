package com.windranger.reminder.ui.desease

import android.support.v7.util.DiffUtil
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVListener
import com.windranger.reminder.base.recycler.RVViewHolder
import com.windranger.reminder.base.recycler.RvAdapter
import com.windranger.reminder.model.desease.Desease
import com.windranger.reminder.model.desease.form.DeseaseForm
import com.windranger.reminder.model.desease.form.DeseaseItem
import kotlinx.android.synthetic.main.item_desease.view.*

class DeseaseAdapter : RvAdapter<Desease, DeseaseAdapter.DeseaseVH>(DeseaseDiff()) {
    private val selectState = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeseaseVH {
        return DeseaseVH(getView(R.layout.item_desease, parent))
    }

    inner class DeseaseVH(itemView: View) : RVViewHolder<Desease>(itemView) {

        override fun onItemClicked(listener: RVListener) {
            val state = selectState.get(adapterPosition, false)
            setIsActive(!state)
            selectState.put(adapterPosition, !state)
        }

        override fun bind(item: Desease) {
            setIsActive(selectState.get(adapterPosition, false))
            itemView.tvTitle.text = item.name
        }

        private fun setIsActive(isActive: Boolean) {
            itemView.apply {
                root.isActivated = isActive
                tvTitle.isActivated = isActive
            }
        }

    }

    fun getForm(): DeseaseForm {
        val list = mutableListOf<DeseaseItem>()

        for (i in 0 until itemCount) {
            if (selectState[i]) {
                list.add(DeseaseItem(getItem(i).id))
            }
        }

        return DeseaseForm(list)
    }


    class DeseaseDiff : DiffUtil.ItemCallback<Desease>() {
        override fun areItemsTheSame(oldItem: Desease?, newItem: Desease?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Desease?, newItem: Desease?): Boolean {
            return oldItem?.name == newItem?.name
        }
    }

}