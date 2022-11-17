package com.looktabinc.feature.main

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
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
    }

    companion object {
        fun newInstance() = HomeFragment()
    }


}