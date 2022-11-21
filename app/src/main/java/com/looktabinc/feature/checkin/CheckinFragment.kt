package com.looktabinc.feature.checkin

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentCheckinBinding
import com.looktabinc.feature.main.MainViewModel
import com.looktabinc.feature.mypage.HistoryAdapter

class CheckinFragment : BaseFragment<FragmentCheckinBinding>(
    R.layout.fragment_checkin
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    private val checkinAdapter by lazy {
        CheckinAdapter().apply {
            setOnItemClickListener(object : CheckinAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
                    activityViewModel.selectCheckIn(id)
                    activityViewModel.setViewFlow(MainViewModel.ViewFlow.REVIEW_WRITE)
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
        binding.rvCheckin.apply {
            adapter = checkinAdapter
            activityViewModel.getCheckin()
        }
    }

    override fun initObserves() {
        activityViewModel.checkinList.observe(viewLifecycleOwner) { it ->
            checkinAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = CheckinFragment()
    }


}