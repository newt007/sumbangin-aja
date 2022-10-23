package com.bintangpoetra.sumbanginaja.presentation.region.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bintangpoetra.sumbanginaja.databinding.RegionItemBinding
import com.bintangpoetra.sumbanginaja.domain.region.model.Region

class RegionAdapter(private val onClick: (region: Region) -> Unit) :
    PagingDataAdapter<Region, RegionAdapter.RegionViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        getItem(position)?.let { region ->
            holder.bind(region)
            holder.itemView.setOnClickListener {
                onClick(region)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding =
            RegionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding)
    }

    inner class RegionViewHolder(private val binding: RegionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(region: Region) {
            binding.tvName.text = region.name
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Region> =
            object : DiffUtil.ItemCallback<Region>() {
                override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
                    return oldItem.id == newItem.id && oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
                    return oldItem == newItem
                }
            }
    }
}