package com.looktabinc.feature.wallet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.looktabinc.R
import com.looktabinc.databinding.ItemNftBinding

class NftAdapter :
    ListAdapter<NftResponse, NftAdapter.CategoryViewHolder>(SubCategotyComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_nft, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemNftBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: NftResponse) {
            binding?.item = item

            itemView.setOnClickListener {
                mListener?.onItemClick(item.token_id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}

object SubCategotyComparator : DiffUtil.ItemCallback<NftResponse>() {
    override fun areItemsTheSame(
        oldItem: NftResponse,
        newItem: NftResponse
    ): Boolean {
        return oldItem.token_id == newItem.token_id
    }

    override fun areContentsTheSame(
        oldItem: NftResponse,
        newItem: NftResponse
    ): Boolean {
        return oldItem == newItem
    }
}