package com.looktabinc.feature.main

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.base.eventObserve
import com.looktabinc.databinding.FragmentReviewHistoryBinding

class ReviewHistoryFragment : BaseFragment<FragmentReviewHistoryBinding>(
    R.layout.fragment_review_history
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun initViews() {
        binding.viewModel = activityViewModel
    }

    override fun initObserves() {
        activityViewModel.reviewHistoryEvent.eventObserve(viewLifecycleOwner) { text ->
            when (text) {
                activityViewModel.close -> {
                    closeFragment()
                }
            }
        }
    }


    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@ReviewHistoryFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = ReviewHistoryFragment()
    }


}