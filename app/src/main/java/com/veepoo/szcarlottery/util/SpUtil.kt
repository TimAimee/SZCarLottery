package com.veepoo.szcarlottery.util

import android.content.Context
import android.content.SharedPreferences

object SpUtil {

    var SP_NAME = "lotterry"
    private var sp: SharedPreferences? = null

    fun clear(context: Context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().clear().commit()
    }

    fun saveString(context: Context, key: String, value: String) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putString(key, value).commit()
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putBoolean(key, value).commit()
    }

    fun getString(context: Context, key: String, defValue: String): String? {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        return sp!!.getString(key, defValue)
    }

    fun getBoolean(
        context: Context, key: String,
        defValue: Boolean
    ): Boolean {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        return sp!!.getBoolean(key, defValue)
    }

    fun getInt(context: Context, key: String, defValue: Int): Int {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        return sp!!.getInt(key, defValue)
    }

    fun getLong(context: Context, key: String, defValue: Long): Long {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        return sp!!.getLong(key, defValue)
    }

    fun getFloat(context: Context, key: String, defValue: Float): Float {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        return sp!!.getFloat(key, defValue)
    }

    fun cleanData(context: Context, key: String) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putString(key, "").commit()
    }

    fun saveInt(context: Context, key: String, value: Int) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putInt(key, value).commit()
    }

    fun saveLong(context: Context, key: String, value: Long) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putLong(key, value).commit()
    }

    fun saveFloat(context: Context, key: String, value: Float) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0)
        sp!!.edit().putFloat(key, value).commit()
    }


}
