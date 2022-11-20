package com.looktabinc.feature.wallet

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.knear.android.provider.response.functioncall.transaction.FunctionCallTransactionResponse
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(
    R.layout.fragment_detail
) {
    private val activityViewModel: NearViewModel by activityViewModels()

    override fun initViews() {
        binding.viewModel = activityViewModel
        Log.e("selectTokenId", "${activityViewModel.selectTokenId}")
        initBtn()
    }

    private fun initBtn() {
        binding.ivBack.setOnClickListener {
            closeFragment()
        }
        binding.btnBurn.setOnClickListener {
            (activity as NearActivity).sendBurn(activityViewModel.selectTokenId)
            binding.txProgress.visibility = View.VISIBLE
        }
    }


    fun updateTxResponse(callFunctionResponse: FunctionCallTransactionResponse) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {
            val str = it.transaction.hash
            Log.e("hash     ", "${str}")
            closeFragment()

        }
    }

    private fun closeFragment() {
        (activity as NearActivity).sendViewAccount()
        (activity as NearActivity).sendTransaction()
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@DetailFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = DetailFragment()
    }

}
