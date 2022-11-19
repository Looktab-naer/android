package com.looktabinc.feature.wallet

import android.view.View
import com.knear.android.provider.response.sendmoney.SendMoney
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentTransactionBinding

class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    R.layout.fragment_transaction
) {

    override fun initViews() {
        binding.transactionBtnCv.setOnClickListener {
            (activity as NearActivity).sendTransaction()
            binding.txProgress.visibility = View.VISIBLE
        }
        binding.callFunctionBtnCv.setOnClickListener {
            (activity as NearActivity).sendTransaction()
            binding.txProgress.visibility = View.VISIBLE
        }
    }

    fun updateTxResponse(callFunctionResponse: SendMoney) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {
            binding.serviceReponseTv.text = "Success with hash: $it"
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
