package com.david.metropolitanmuseumofart.presentation_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.david.metropolitanmuseumofart.presentation_search.databinding.ListSearchItemBinding

class SearchListAdapter(
    private val onItemClickListener : OnItemClickListener
) : ListAdapter<SearchedListItemModel, SearchListAdapter.ViewHolder>(MyItemDiffCallback()) {

    class ViewHolder(private val binding: ListSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchedListItemModel) {
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
            onItemClickListener.onItemClick(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class MyItemDiffCallback : DiffUtil.ItemCallback<SearchedListItemModel>() {
        override fun areItemsTheSame(
            oldItem: SearchedListItemModel,
            newItem: SearchedListItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchedListItemModel,
            newItem: SearchedListItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: SearchedListItemModel)
    }

}