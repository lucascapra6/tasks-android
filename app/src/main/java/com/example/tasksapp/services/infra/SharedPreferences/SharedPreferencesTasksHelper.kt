package com.example.tasksapp.services.infra.SharedPreferences

import android.content.Context

class SharedPreferencesTasksHelper(context: Context): AbstractSharedPreferencesHelper(context) {
    override val prefName = "tasks"
    override fun get(key: String): String {
        return preferences.getString(key, "") ?: ""
    }

    override fun store(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}