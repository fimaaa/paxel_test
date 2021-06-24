package com.example.paxeltest.preference

import android.content.Context
import javax.inject.Inject

class PrefManagerImp @Inject constructor(context: Context) : PrefManager {

    companion object {
        const val USER_NAME = "user_name"
        const val WEBSITE = "website"
    }

    private val contextMode = Context.MODE_PRIVATE
    private var pref = context.getSharedPreferences("FRMK_PREFS", contextMode)
    private var editor = pref.edit()

    var prefLocale: String?
        get() {
            return pref.getString("FRMK_USER_LANGUAGE", "en")
        }
        set(value) {
            if (value != null) {
                editor.putString("FRMK_USER_LANGUAGE", value)?.apply()
            }
        }

    var prefUserToken: String?
        get() {
            return pref.getString("FRMK_USER_TOKEN", null)
        }
        set(value) {
            editor.putString("FRMK_USER_TOKEN", value)?.apply()
        }

    var prefRefreshToken: String?
        get() {
            return pref.getString("FRMK_REFRESH_TOKEN", null)
        }
        set(value) {
            editor.putString("FRMK_REFRESH_TOKEN", value)?.apply()
        }

    fun clearPreferences() {
        editor.clear().apply()
    }
}