package com.looktabinc.feature.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.looktabinc.R
import com.looktabinc.databinding.ItemReviewBinding
import com.looktabinc.feature.model.ReviewHistory


class HistoryAdapter :
    ListAdapter<ReviewHistory, HistoryAdapter.BrandViewHolder>(HistoryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemReviewBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        fun bind(item: ReviewHistory) {
            binding?.item = item
//
//            binding?.layout?.setOnClickListener {
//                mListener?.onItemClick(item.id)
//            }

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

object HistoryComparator : DiffUtil.ItemCallback<ReviewHistory>() {
    override fun areItemsTheSame(
        oldItem: ReviewHistory,
        newItem: ReviewHistory
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ReviewHistory,
        newItem: ReviewHistory
    ): Boolean {
        return oldItem == newItem
    }
}