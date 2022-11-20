package com.looktabinc.feature.main

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentNeighborhoodBinding
import com.looktabinc.feature.checkin.CheckinFragment

class NeighborhoodFragment : BaseFragment<FragmentNeighborhoodBinding>(
    R.layout.fragment_neighborhood
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    var count = 0
    var all = 0
    override fun initViews() {
        all = activityViewModel.neighborhoodList.value?.size ?: 0
        binding.viewModel = activityViewModel
        activityViewModel.getNeighborhoodItem(0)
        count++
        binding.ivImg.setOnClickListener {
            if (all == count) {
                closeFragment()
            } else {
                activityViewModel.getNeighborhoodItem(count++)
            }
        }
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@NeighborhoodFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = NeighborhoodFragment()
    }
}