package com.looktabinc.feature.wallet

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.knear.android.provider.response.functioncall.FunctionCallResponse
import com.knear.android.provider.response.viewaccount.ViewAccountResult
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.customview.LinearGradientSpan
import com.looktabinc.databinding.FragmentTransactionBinding

class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    R.layout.fragment_transaction
) {
    private val activityViewModel: NearViewModel by activityViewModels()

    private val nftAdapter by lazy {
        NftAdapter().apply {
            setOnItemClickListener(object : NftAdapter.OnItemClickListener {
                override fun onItemClick(id: String) {
                    activityViewModel.postNftItem(id)
                    (activity as NearActivity).addDetailFragment()

                }
            })
        }
    }

    override fun initViews() {
        initCall()
        initRecyclerView()
    }

    fun initCall() {
        (activity as NearActivity).sendViewAccount()
        (activity as NearActivity).sendTransaction()
        binding.txProgress.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.rvNft.apply {
            adapter = nftAdapter
            itemAnimator = null
        }
    }

    override fun initObserves() {
        activityViewModel.nftList.observe(this) {
            nftAdapter.submitList(it)
        }
    }

    private fun nearGradient(str:String) {
        val size = str.length
        binding.tvNear.apply {
            val purple = ContextCompat.getColor(context, R.color.start)
            val teal = ContextCompat.getColor(context, R.color.end)
            val spannable = str.toSpannable()
            spannable[0..size] = LinearGradientSpan(str, str, purple, teal)
            text = spannable
        }
    }
    fun updateTxResponse0(callFunctionResponse: ViewAccountResult) {
        callFunctionResponse.result.let {
            val len = it.amount.length
            val near =
                it.amount.substring(0, len - 24) + "." + it.amount.substring(len - 24, len - 22)
            val usd = (it.amount.substring(0, len - 24).toInt() * 1.73)
            nearGradient( "$near NEAR")
            binding.tvUsd.text = "${usd} USD"
        }
        callFunctionResponse.error?.let {
            Log.i("err",  "Error: ${it.message}")
        }
    }

    fun updateTxResponse(callFunctionResponse: FunctionCallResponse) {
        binding.txProgress.visibility = View.GONE
        callFunctionResponse.result.let {
            val functionResult = it.result!!.getListDecodedAsciiValue()
            Log.i("NearService", "${functionResult?.toList()}")
            functionResult?.toList()?.let { it1 -> activityViewModel.postNft(it1) }
        }
        callFunctionResponse.error?.let {
            Log.i("err",  "Error: ${it.message}")
        }
    }


    private fun ByteArray.getListDecodedAsciiValue(): Array<NftResponse>? {
        val unsignedToBytes: List<Byte> =
            this.map { byte: Byte -> (byte.toInt() and 0xff).toByte() }
        val asciiValueBytes = unsignedToBytes.toByteArray()
        val stringValue = asciiValueBytes.toString(Charsets.US_ASCII)
        // Deserialized as string
        return Gson().fromJson(stringValue, Array<NftResponse>::class.java)
    }


    companion object {
        fun newInstance() = TransactionFragment()
    }

}
