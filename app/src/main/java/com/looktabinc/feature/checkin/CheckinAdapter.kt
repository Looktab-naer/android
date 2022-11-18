package com.looktabinc.feature.checkin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.looktabinc.R
import com.looktabinc.databinding.ItemCheckinBinding
import com.looktabinc.feature.model.Checkin


class CheckinAdapter :
    ListAdapter<Checkin, CheckinAdapter.BrandViewHolder>(CheckinComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_checkin, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemCheckinBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        fun bind(item: Checkin) {
            binding?.item = item
            binding?.btnWrite?.setOnClickListener {
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

object CheckinComparator : DiffUtil.ItemCallback<Checkin>() {
    override fun areItemsTheSame(
        oldItem: Checkin,
        newItem: Checkin
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Checkin,
        newItem: Checkin
    ): Boolean {
        return oldItem == newItem
    }
}