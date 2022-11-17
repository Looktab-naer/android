package com.looktabinc.feature.main

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.base.eventObserve
import com.looktabinc.databinding.FragmentAirdropSettingBinding

class AirDropSettingFragment  : BaseFragment<FragmentAirdropSettingBinding>(
    R.layout.fragment_airdrop_setting
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
        activityViewModel.airDropEvent.eventObserve(viewLifecycleOwner) { text ->
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
            it.beginTransaction().remove(this@AirDropSettingFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = AirDropSettingFragment()
    }
}