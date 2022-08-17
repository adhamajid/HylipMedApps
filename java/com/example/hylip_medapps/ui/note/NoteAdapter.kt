package com.windranger.reminder.ui.note

import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.windranger.reminder.R
import com.windranger.reminder.base.recycler.RVListener
import com.windranger.reminder.base.recycler.RVViewHolder
import com.windranger.reminder.base.recycler.RvAdapter
import com.windranger.reminder.model.note.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val listener: RVListener) : RvAdapter<Note, NoteAdapter.NoteVH>(NoteDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        return NoteVH(getView(R.layout.item_note, parent))
    }

    inner class NoteVH(itemView: View) : RVViewHolder<Note>(itemView, listener) {

        override fun bind(item: Note) {
            itemView.apply {
                tvTitle.text = item.title
                tvContent.text = item.content
            }
        }

    }

    class NoteDiff : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note?, newItem: Note?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Note?, newItem: Note?): Boolean {
            return oldItem?.title == newItem?.title && oldItem?.content == newItem?.content
        }

    }
}