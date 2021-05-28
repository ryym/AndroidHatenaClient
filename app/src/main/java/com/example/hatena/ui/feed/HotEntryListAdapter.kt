package com.example.hatena.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hatena.databinding.ListItemHeadHotEntryBinding
import com.example.hatena.databinding.ListItemHotEntryBinding
import com.example.hatena.model.HotEntry

private const val VIEW_TYPE_HEAD = 0
private const val VIEW_TYPE_NORMAL = 1

class HotEntryListAdapter(private val onEntryClick: HotEntryClickListener) :
    ListAdapter<HotEntry, HotEntryListAdapter.ViewHolder>(HotEntryDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEAD else VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEAD -> HeadViewHolder.from(parent)
            else -> NormalViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry, onEntryClick)
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(entry: HotEntry, onEntryClick: HotEntryClickListener)
    }

    class HeadViewHolder private constructor(private val binding: ListItemHeadHotEntryBinding) :
        ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeadViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHeadHotEntryBinding.inflate(layoutInflater, parent, false)
                return HeadViewHolder(binding)
            }
        }

        override fun bind(entry: HotEntry, onEntryClick: HotEntryClickListener) {
            binding.entry = entry
            binding.entryClickListener = onEntryClick
            binding.executePendingBindings()
        }
    }

    class NormalViewHolder private constructor(private val binding: ListItemHotEntryBinding) :
        ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): NormalViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHotEntryBinding.inflate(layoutInflater, parent, false)
                return NormalViewHolder(binding)
            }
        }

        override fun bind(entry: HotEntry, onEntryClick: HotEntryClickListener) {
            binding.entry = entry
            binding.entryClickListener = onEntryClick
            binding.executePendingBindings()
        }
    }
}

private class HotEntryDiffCallback : DiffUtil.ItemCallback<HotEntry>() {
    override fun areItemsTheSame(oldItem: HotEntry, newItem: HotEntry): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(oldItem: HotEntry, newItem: HotEntry): Boolean {
        return oldItem == newItem
    }
}

class HotEntryClickListener(val listener: (entry: HotEntry) -> Unit) {
    fun onClick(entry: HotEntry) = listener(entry)
}