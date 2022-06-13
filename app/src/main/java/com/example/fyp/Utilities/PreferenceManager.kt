package com.example.fyp.Utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.fyp.Utilities.Constants

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun putBoolean(key: String?, value: Boolean?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String?, value: String?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, null)
    }

    fun removeString(key: String?) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().commit()
    }
}