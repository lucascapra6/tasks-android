package com.example.tasksapp.services.infra.SharedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

abstract class AbstractSharedPreferencesHelper(context: Context) {
    protected abstract val prefName: String
    protected val preferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    abstract fun get(key: String) : String
    abstract fun store(key: String, value: String)
    abstract fun remove(key: String, value: String)
}