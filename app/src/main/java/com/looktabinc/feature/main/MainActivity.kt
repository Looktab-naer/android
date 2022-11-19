package com.looktabinc.feature.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.looktabinc.R
import com.looktabinc.base.BaseActivity
import com.looktabinc.data.injection.KakaoInjection
import com.looktabinc.databinding.ActivityMainBinding
import com.looktabinc.feature.ar.ArActivity
import com.looktabinc.feature.checkin.CheckinFragment
import com.looktabinc.feature.checkin.ReviewWriteFragment
import com.looktabinc.feature.main.MainViewModel.*
import com.looktabinc.feature.mypage.AirDropSettingFragment
import com.looktabinc.feature.mypage.MyPageFragment
import com.looktabinc.feature.mypage.ReviewHistoryFragment
import com.looktabinc.feature.wallet.NearActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
                KakaoInjection.provideRepository(),
            )
        ).get(MainViewModel::class.java)
    }


    var my = MyPageFragment.newInstance()
    var home = HomeFragment.newInstance()
    var checkin = CheckinFragment.newInstance()

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
        changeHome()

        viewModel.flow.observe(this, Observer {
            Log.e("flow", it.name)
            binding.btnHistory.isActivated=false
            binding.btnMypage.isActivated=false
            when (it) {
                ViewFlow.HOME -> {
                    changeHome()
                }
                ViewFlow.MYCHECKIN -> {
                    binding.btnHistory.isActivated=true
                    changeCheckin()
                }
                ViewFlow.MYPAGE -> {
                    binding.btnMypage.isActivated=true
                    changeMypage()
                }
                ViewFlow.REVIEW_HISTORY -> {
                    changeOutContainerFragment(ReviewHistoryFragment.newInstance())
                }
                ViewFlow.AIRDROP_SETTING -> {
                    changeOutContainerFragment(AirDropSettingFragment.newInstance())
                }
                ViewFlow.REVIEW_WRITE -> {
                    addOutFragment(ReviewWriteFragment.newInstance())
                }
                ViewFlow.WALLET->{
                    NearActivity.start(this)
                }
                ViewFlow.AR->{
                    ArActivity.start(this)
                }
                else -> {}
            }
        })
    }


    fun changeMypage() {
        if (!my.isAdded) {
            addFragment(my)
            changeShowFragment(my)
        } else {
            changeShowFragment(my)
            changeHideFragment(home)
            changeHideFragment(checkin)
        }
    }

    fun changeCheckin() {
        if (!checkin.isAdded) {
            addFragment(checkin)
            changeShowFragment(checkin)
        } else {
            changeShowFragment(checkin)
            changeHideFragment(my)
            changeHideFragment(home)
        }
    }

    fun changeHome() {
        if (!home.isAdded) {
            addFragment(home)
            changeShowFragment(home)
        } else {
            changeShowFragment(home)
            changeHideFragment(my)
            changeHideFragment(checkin)
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment)
        }.commit()
    }

    private fun changeHideFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            hide(fragment)
        }.commit()
    }

    private fun changeShowFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            show(fragment)
        }.commit()
    }

    private fun changeOutContainerFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.out_fragment_container, fragment)
        }.commit()
    }

    private fun addOutFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.out_fragment_container, fragment)
        }.commit()
    }


    override fun onBackPressed() {
        when (viewModel.flow.value) {
            ViewFlow.HOME,
            ViewFlow.MYCHECKIN,
            ViewFlow.MYPAGE -> {
                super.onBackPressed()
            }

            ViewFlow.REVIEW_HISTORY -> {
                viewModel.onClickReviewHistoryFragmentEvent(viewModel.close)
            }
            ViewFlow.AIRDROP_SETTING -> {
                viewModel.onClickAirDropFragmentEvent(viewModel.close)
            }
            ViewFlow.REVIEW_WRITE -> {
                viewModel.onClickReviewWriteFragmentEvent(viewModel.close)
            }
            else -> {}
        }

    }
}
