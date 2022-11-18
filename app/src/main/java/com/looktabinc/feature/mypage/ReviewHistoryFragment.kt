package com.looktabinc.feature.mypage

import android.content.Context
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.base.eventObserve
import com.looktabinc.databinding.FragmentReviewHistoryBinding
import com.looktabinc.feature.main.MainViewModel

class ReviewHistoryFragment : BaseFragment<FragmentReviewHistoryBinding>(
    R.layout.fragment_review_history
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    private val historyAdapter by lazy {
        HistoryAdapter().apply {
            setOnItemClickListener(object : HistoryAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
//                    activityViewModel.brandSeq = id
//                    viewModel.onClickEvent(viewModel.brand)
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun initViews() {
        binding.viewModel = activityViewModel
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.rvReview.apply {
            adapter = historyAdapter
            activityViewModel.getHistory()
        }
    }

    override fun initObserves() {
        activityViewModel.historyList.observe(viewLifecycleOwner) { it ->
            historyAdapter.submitList(it)
        }
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