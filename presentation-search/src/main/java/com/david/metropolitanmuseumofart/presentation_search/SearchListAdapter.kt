package com.david.metropolitanmuseumofart.presentation_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.david.metropolitanmuseumofart.presentation_search.databinding.ListSearchItemBinding

class SearchListAdapter(
    private val onListeners: OnListeners,
) : ListAdapter<Int, SearchListAdapter.ViewHolder>(MyItemDiffCallback()) {

    class ViewHolder(private val binding: ListSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListSearchItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_search_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onListeners.onItemClick(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class MyItemDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Int>,
        currentList: MutableList<Int>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    interface OnListeners {
        fun onItemClick(item: Int)
        fun onListChange()
    }

}