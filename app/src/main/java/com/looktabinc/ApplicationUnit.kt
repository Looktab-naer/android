package com.looktabinc

import android.content.SharedPreferences

object ApplicationUnit {
    const val sharedPrefFile = "app_preferences"
    private const val access_token = "accessToken"
    private const val refresh_token = "refreshToken"
    private const val username_value = "username"
    private const val channel_talk_id = "channelTalkId"
    private const val is_first = ""
    lateinit var mPreferences: SharedPreferences

    val String.Companion.Empty
        get() = ""

    val accessToken: String?
        get() = HashShopApplication.INSTANCE.sharedPreferences.getString(
            access_token, null
        )

    val refreshToken: String?
        get() = HashShopApplication.INSTANCE.sharedPreferences.getString(
            refresh_token, null
        )
    val userNameValue: String?
        get() = HashShopApplication.INSTANCE.sharedPreferences.getString(
            username_value, null
        )

    val channelTalkId: String?
        get() = HashShopApplication.INSTANCE.sharedPreferences.getString(
            channel_talk_id, null
        )
    val isFirst: String?
        get() = HashShopApplication.INSTANCE.sharedPreferences.getString(
            is_first, null
        )

    fun removeToken() {
        val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
        accessToken?.let {
            preferencesEditor.putString(access_token, null)
        }
        refreshToken?.let {
            preferencesEditor.putString(refresh_token, null)
        }
        userNameValue?.let {
            preferencesEditor.putString(username_value, null)
        }
        preferencesEditor.apply()
    }

    fun removeIsFirst() {
        val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
        isFirst?.let {
            preferencesEditor.putString(is_first, null)
        }
        preferencesEditor.apply()
    }

    fun putAccessToken(accessToken: String?) {
        accessToken?.let {
            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
            preferencesEditor.putString(access_token, it)
            preferencesEditor.apply()
        }
    }

    fun putRefreshToken(refreshToken: String?) {
        refreshToken?.let {
            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
            preferencesEditor.putString(refresh_token, it)
            preferencesEditor.apply()
        }
    }

    fun putUserNameValue(userNameValue: String?) {
        userNameValue?.let {
            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
            preferencesEditor.putString(username_value, it)
            preferencesEditor.apply()
        }
    }

    fun putChannelTalkId(channelTalkId: String?) {
        channelTalkId?.let {
            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
            preferencesEditor.putString(channel_talk_id, it)
            preferencesEditor.apply()
        }
    }

    fun putIsFirst(isFirst: String?) {
        isFirst?.let {
            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
            preferencesEditor.putString(is_first, it)
            preferencesEditor.apply()
        }
    }

}
