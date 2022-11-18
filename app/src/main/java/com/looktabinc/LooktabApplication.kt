package com.looktabinc

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class LooktabApplication : Application() {

    companion object {
        lateinit var INSTANCE: LooktabApplication
    }

    val sharedPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            "app_preferences",
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

    }
}