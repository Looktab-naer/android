package com.looktabinc.feature.wallet

import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {

    override fun initViews() {
        binding.btnLogin.setOnClickListener {
//            (activity as NearActivity).login("yuchoco.testnet")
            val email = binding.userEmailEt.text.toString()
            if (email.isNotEmpty()) {
                (activity as NearActivity).login(email)
            }
        }
        binding.btnFinsh.setOnClickListener {
            (activity as NearActivity).finish()
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

}
