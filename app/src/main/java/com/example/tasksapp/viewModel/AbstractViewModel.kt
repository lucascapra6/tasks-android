package com.example.tasksapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.R
import com.example.tasksapp.models.ValidationApiModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.utils.Constants

abstract class AbstractViewModel(private val applicationContext: Application): AndroidViewModel(applicationContext) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(applicationContext)

    fun stringfyResource(R: Int): String {
        return applicationContext.getString(R)
    }
    //enviar para o retrofit funcao de deslogar
    fun logout() {
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.PERSON_KEY)
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.TOKEN)
        sharedPreferencesTasksHelper.remove(Constants.SharedPreferencesKeys.NAME)
    }
}