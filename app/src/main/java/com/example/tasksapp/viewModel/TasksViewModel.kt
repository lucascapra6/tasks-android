package com.example.tasksapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.models.ValidationApiModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.repository.remote.tasks.TasksRepository
import com.example.tasksapp.services.useCases.FetchTasksUseCase
import com.example.tasksapp.utils.Constants
import kotlinx.coroutines.launch
import kotlin.math.log

class TasksViewModel(application: Application) : AbstractViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val repository = TasksRepository()
    private val _tasksState = MutableLiveData<TaskState>()
    val tasksState: LiveData<TaskState> = _tasksState
    private val _loadingTasks = MutableLiveData<Boolean>()
    val loadingTasks: LiveData<Boolean> = _loadingTasks
    private val _deleteTask = MutableLiveData<ValidationApiModel<Boolean>>()
    val deleteTask: LiveData<ValidationApiModel<Boolean>> = _deleteTask
    private val token = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.TOKEN)
    private val personKey = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.PERSON_KEY)
    private val fetchTasksUseCase = FetchTasksUseCase(repository)
    fun fetchTasks(taskArgument: Int) {
        RetrofitClient.addHeaders(token, personKey)

        viewModelScope.launch {
            _tasksState.value = TaskState.Loading
            val response = fetchTasksUseCase.invoke(taskArgument)

            _tasksState.value = when {
                response.isSuccess -> TaskState.Success(response.getOrNull() ?: emptyList())
                else -> {TaskState.Error("Fail on load tasks")}
            }
        }
    }

    fun deleteTask(id: String) {
        RetrofitClient.addHeaders(token, personKey)
        viewModelScope.launch {
            try {
                val response = repository.deleteTask(id)
                _deleteTask.value = ValidationApiModel(response, true)
            } catch (e: Exception) {
                _deleteTask.value = ValidationApiModel(null, false, "Fail on delete task")
            }
        }
    }
}

sealed class TaskState {
    data object Loading : TaskState()
    data class Success(val tasks: List<TaskModel>) : TaskState()
    data class Error(val message: String) : TaskState()
}