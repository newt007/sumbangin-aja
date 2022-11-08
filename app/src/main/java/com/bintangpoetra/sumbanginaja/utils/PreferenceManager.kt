package com.bintangpoetra.sumbanginaja.utils

import android.content.Context
import android.content.SharedPreferences
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_ADDRESS
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_EMAIL
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_IS_LOGIN
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_NAME
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_ROLE
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_TOKEN
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_USER_ID
import com.bintangpoetra.sumbanginaja.utils.ConstVal.KEY_USER_NAME
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString

class PreferenceManager(context: Context) {

    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(ConstVal.PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setStringPreference(prefKey: String, value: String) {
        editor.putString(prefKey, value)
        editor.apply()
    }

    fun setBooleanPreference(prefKey: String, value: Boolean) {
        editor.putBoolean(prefKey, value)
        editor.apply()
    }

    fun clearPreferenceByKey(prefKey: String) {
        editor.remove(prefKey)
        editor.apply()
    }

    fun storeLoginData(user: User) {
        setStringPreference(KEY_USER_ID, user.id.toString())
        setStringPreference(KEY_NAME, user.name)
        setStringPreference(KEY_USER_NAME, user.profileUsers)
        setStringPreference(KEY_EMAIL, user.email)
        setStringPreference(KEY_ADDRESS, user.address)
        setStringPreference(KEY_TOKEN, user.token)
        setBooleanPreference(KEY_IS_LOGIN, true)
    }

    fun clearAllPreferences() {
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_USER_NAME)
        editor.remove(KEY_ADDRESS)
        editor.remove(KEY_IS_LOGIN)
        editor.remove(KEY_NAME)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_TOKEN)
        editor.apply()
    }

    val getUserId = prefs.getString(KEY_USER_ID, "")
    val isLogin = prefs.getBoolean(KEY_IS_LOGIN, false)
    val getUserName = prefs.getString(KEY_USER_NAME, "")
    val getName = prefs.getString(KEY_NAME, "")
    val getUserRole = prefs.getString(KEY_ROLE, "")
    val getEmail = prefs.getString(KEY_EMAIL, "")
    val getToken = prefs.getString(KEY_TOKEN, "") ?: emptyString()

}