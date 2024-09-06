package com.example.tasksapp.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.utils.Constants

abstract class AbstractActivity: AppCompatActivity() {
    private lateinit var sharedPreferencesTasksHelper: SharedPreferencesTasksHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(this)
    }

    fun logout() {
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.PERSON_KEY)
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.TOKEN)
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.NAME)
    }
}