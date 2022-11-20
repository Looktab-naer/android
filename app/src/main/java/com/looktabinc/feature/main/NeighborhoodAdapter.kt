package com.looktabinc.feature.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.looktabinc.R
import com.looktabinc.databinding.ItemNeighborhoodBinding
import com.looktabinc.feature.model.Neighborhood

class NeighborhoodAdapter :
    ListAdapter<Neighborhood, NeighborhoodAdapter.CategoryViewHolder>(SubCategotyComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_neighborhood, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemNeighborhoodBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Neighborhood) {
            binding?.item = item

            itemView.setOnClickListener {
                mListener?.onItemClick(item.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}

object SubCategotyComparator : DiffUtil.ItemCallback<Neighborhood>() {
    override fun areItemsTheSame(
        oldItem: Neighborhood,
        newItem: Neighborhood
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Neighborhood,
        newItem: Neighborhood
    ): Boolean {
        return oldItem == newItem
    }
}