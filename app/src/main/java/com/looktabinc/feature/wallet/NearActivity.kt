package com.looktabinc.feature.wallet

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.knear.android.service.NearMainService
import com.looktabinc.R
import com.looktabinc.base.BaseActivity
import com.looktabinc.databinding.ActivityNearBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NearActivity : BaseActivity<ActivityNearBinding>(R.layout.activity_near) {

    private var loginFragment: LoginFragment = LoginFragment.newInstance()
    private var transactionFragment: TransactionFragment = TransactionFragment.newInstance()
    private var detailFragment: DetailFragment = DetailFragment.newInstance()
    private lateinit var nearMainService: NearMainService


    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore, NearViewModelFactory(
            )
        ).get(NearViewModel::class.java)
    }

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
        supportFragmentManager.beginTransaction().add(R.id.demo_fragment_container, loginFragment)
            .commit()
        nearMainService = NearMainService(this)
    }


    fun login(email: String) {
        viewModel.user = email
        nearMainService.login(email)
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if (nearMainService.attemptLogin(uri)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.demo_fragment_container, transactionFragment).commit()
        }
    }


    fun addDetailFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.demo_fragment_container, detailFragment)
        }.commit()
    }


    fun sendViewAccount() {
        CoroutineScope(Dispatchers.IO).launch {
            val balanceOfResponse = nearMainService.viewAccount()
            withContext(Dispatchers.Main) {
                transactionFragment.updateTxResponse0(balanceOfResponse)
            }
        }
    }

    fun sendTransaction() {

        val contractName = "testm7.testnet"
        val methodName = "nft_tokens_for_owner"
        val balanceOfArgs = "{\"account_id\":\"${nearMainService.accountId}\"}"
        Log.e("account_i ", balanceOfArgs)
        CoroutineScope(Dispatchers.IO).launch {
            val transaction =
                nearMainService.callViewFunction(contractName, methodName, balanceOfArgs)
            withContext(Dispatchers.Main) {
                transactionFragment.updateTxResponse(transaction)
            }
        }
    }

    fun sendBurn(token_id: String) {
        val contractName = "testm7.testnet"
        val methodName = "nft_burn"
        val balanceOfArgs = "{ \"token_id\": \"${token_id}\" }"
        Log.e("12321", balanceOfArgs)
        CoroutineScope(Dispatchers.IO).launch {
            val transaction =
                nearMainService.callViewFunctionTransaction(contractName, methodName, balanceOfArgs)
            withContext(Dispatchers.Main) {
                detailFragment.updateTxResponse(transaction)
            }
        }
    }

    fun sendToast() {
        Toast.makeText(this, "Failed to load NFT information", Toast.LENGTH_SHORT).show();
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, NearActivity::class.java)
        }

        fun start(context: Context?, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, NearActivity::class.java).apply(action)
            context?.startActivity(intent)
        }
    }

}
