package com.example.tasksapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.models.ValidationApiModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.repository.remote.tasks.TasksRepository
import com.example.tasksapp.utils.Constants
import kotlinx.coroutines.launch

class TasksViewModel(application: Application) : AbstractViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val repository = TasksRepository()
    private val _tasks = MutableLiveData<ValidationApiModel<List<TaskModel>>>()
    private val _loadingTasks = MutableLiveData<Boolean>()
    val tasks: LiveData<ValidationApiModel<List<TaskModel>>> = _tasks
    val loadingTasks: LiveData<Boolean> = _loadingTasks

    fun fetchAllTasks() {
        val token = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.TOKEN)
        val personKey = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.PERSON_KEY)

        RetrofitClient.addHeaders(token, personKey)

        viewModelScope.launch {
            try {
                _loadingTasks.value = true
                val response = repository.fetchAllTasks()
                _tasks.value = ValidationApiModel(response, true)
            } catch (e: Exception) {
                _tasks.value = ValidationApiModel(null, false, "Falha ao carregar tarefas")
            }
            finally {
                _loadingTasks.value = false
            }
        }
    }
}