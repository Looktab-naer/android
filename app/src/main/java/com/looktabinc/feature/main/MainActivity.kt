package com.looktabinc.feature.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.looktabinc.R
import com.looktabinc.base.BaseActivity
import com.looktabinc.data.injection.KakaoInjection
import com.looktabinc.databinding.ActivityMainBinding

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
            when (it) {
                MainViewModel.ViewFlow.HOME -> {
                    changeHome()
                }
                MainViewModel.ViewFlow.MYCHECKIN -> {
                    changeCheckin()
                }
                MainViewModel.ViewFlow.MYPAGE -> {
                    changeMypage()
                }
                MainViewModel.ViewFlow.REVIEW_HISTORY -> {
                    changeOutContainerFragment(ReviewHistoryFragment.newInstance())
                }
                MainViewModel.ViewFlow.AIRDROP_SETTING -> {
                    changeOutContainerFragment(AirDropSettingFragment.newInstance())
                }
                MainViewModel.ViewFlow.REVIEW_WRITE -> {
                    changeOutContainerFragment(AirDropSettingFragment.newInstance())
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

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size <= 1) {
            super.onBackPressed()
        } else {
            viewModel.setMainFlow(MainViewModel.MainFlow.BACK)
        }
    }
}