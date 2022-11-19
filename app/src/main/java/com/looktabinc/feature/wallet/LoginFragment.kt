package com.looktabinc.feature.wallet

import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {

    override fun initViews() {

        binding.loginBtnCv.setOnClickListener {
            val email = binding.userEmailEt.text.toString()
            if (email.isNotEmpty()) {
                (activity as NearActivity).login(email)
            }
        }

    }

    companion object {
        fun newInstance() = LoginFragment()
    }

}
