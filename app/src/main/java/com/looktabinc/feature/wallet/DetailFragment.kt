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

    fun initBtn() {
        binding.ivBack.setOnClickListener {
            closeFragment()
        }
        binding.btnBurn.setOnClickListener {
            (activity as NearActivity).sendBurn(activityViewModel.selectTokenId)
            binding.txProgress.visibility = View.VISIBLE
        }
        binding.btnBurn1.setOnClickListener {
            (activity as NearActivity).sendBurn1(activityViewModel.selectTokenId)
            binding.txProgress.visibility = View.VISIBLE
        }
        binding.btnBurn2.setOnClickListener {
            (activity as NearActivity).sendBurn2(activityViewModel.selectTokenId)
            binding.txProgress.visibility = View.VISIBLE
        }
    }


    fun updateTxResponse(callFunctionResponse: FunctionCallTransactionResponse) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {
            val str = it.transaction.hash
//            var srr2 =str?.decodeBase64()
            binding.serviceReponseTv.text = "$it    \n\n\n Success with hash: ${str}"

//            Log.e("srr2     ", "${srr2}")
            Log.e("hash     ", "${str}")
//            val functionResult = it.result!!.decodeToString()
//            Log.e("NearService", "${functionResult}")
//            closeFragment()
        }
    }

    private fun ByteArray.burnDecodedAsciiValue(): Array<*>? {
        if (this !== null) {
            /**
             * Near returns an array of bytes as result, is an ASCII code of
             * near-sdk-rs and near-sdk-as
             */
            var unsignedToBytes: List<Byte> =
                this.map { byte: Byte -> (byte.toInt() and 0xff).toByte() }
            val asciiValueBytes = unsignedToBytes.toByteArray()
            val stringValue = asciiValueBytes.toString(Charsets.US_ASCII)
            // Deserialized as string
            return Gson().fromJson(stringValue, Array::class.java)
        }
        return null
    }

    private fun closeFragment() {
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
