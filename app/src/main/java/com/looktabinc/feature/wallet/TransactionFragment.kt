package com.looktabinc.feature.wallet

import android.util.Log
import android.view.View
import com.knear.android.provider.response.functioncall.FunctionCallResponse
import com.knear.android.provider.response.viewaccount.ViewAccountResult
import com.knear.android.service.MethodUtils.Companion.getDecodedAsciiValue
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentTransactionBinding

class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    R.layout.fragment_transaction
) {

    override fun initViews() {
        binding.btnAccount.setOnClickListener {
            (activity as NearActivity).sendViewAccount()
            binding.txProgress.visibility = View.VISIBLE
        }
        binding.transactionBtnCv.setOnClickListener {
            (activity as NearActivity).sendTransaction()
            binding.txProgress.visibility = View.VISIBLE
        }
        binding.callFunctionBtnCv.setOnClickListener {
            (activity as NearActivity).requestCallFunction()
            binding.txProgress.visibility = View.VISIBLE
        }
    }

    fun updateTxResponse0(callFunctionResponse: ViewAccountResult) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {

            Log.i("it.result", " ${it}")
            Log.i("it.it.amount", " ${it.amount}")

            val len = it.amount.length
            val near =
                it.amount.substring(0, len - 24) + "." + it.amount.substring(len - 24, len - 22)
            val usd = (it.amount.substring(0, len - 24).toInt() * 1.73)

            binding.tvNear.text = "$near NEAR"
            binding.tvUsd.text = "${usd}USD"
            binding.serviceReponseTv.text = "Success with hash: ${it}"


        }
        callFunctionResponse.error?.let {
            binding.serviceReponseTv.text = "Error: ${it.message}"
        }
    }

    fun updateTxResponse(callFunctionResponse: FunctionCallResponse) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {
            Log.i("it.result", " ${it}")
            val functionResult = callFunctionResponse.result.result!!.getDecodedAsciiValue()
            Log.i("NearService", "$functionResult")
            binding.serviceReponseTv.text = "Success with hash: ${functionResult}"
        }
        callFunctionResponse.error?.let {
            binding.serviceReponseTv.text = "Error: ${it.message}"
        }
    }

    fun updateCallFunctionResponse(callFunctionResponse: StringBuilder) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.let {
            binding.serviceReponseTv.text = "Success: $it"
        }
    }


    companion object {
        fun newInstance() = TransactionFragment()
    }

}
