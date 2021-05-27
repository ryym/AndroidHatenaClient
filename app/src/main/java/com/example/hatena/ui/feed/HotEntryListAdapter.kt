package com.example.hatena.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hatena.api.HotEntry
import com.example.hatena.databinding.ListItemHotEntryBinding

class HotEntryListAdapter :
    ListAdapter<HotEntry, HotEntryListAdapter.ViewHolder>(HotEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }

    class ViewHolder private constructor(private val binding: ListItemHotEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHotEntryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(entry: HotEntry) {
            binding.entry = entry
        }
    }
}

class HotEntryDiffCallback : DiffUtil.ItemCallback<HotEntry>() {
    override fun areItemsTheSame(oldItem: HotEntry, newItem: HotEntry): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(oldItem: HotEntry, newItem: HotEntry): Boolean {
        return oldItem == newItem
    }
}