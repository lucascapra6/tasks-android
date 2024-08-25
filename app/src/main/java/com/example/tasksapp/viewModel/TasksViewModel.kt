package com.example.tasksapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.repository.remote.tasks.TasksRepository
import com.example.tasksapp.utils.Constants
import kotlinx.coroutines.launch

class TasksViewModel(application: Application) : AbstractViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val repository = TasksRepository()
    private val _tasks = MutableLiveData<List<TaskModel>>()
    var tasks: LiveData<List<TaskModel>> = _tasks

    fun fetchAllTasks() {
        val mockTasks = listOf(
            TaskModel(
                id = "1",
                priorityId = "1",
                description = "Estudar Kotlin",
                dueDate = "2024-09-01",
                complete = false
            ),
            TaskModel(
                id = "2",
                priorityId = "2",
                description = "Desenvolver novo projeto Android",
                dueDate = "2024-09-10",
                complete = false
            ),
            TaskModel(
                id = "3",
                priorityId = "3",
                description = "Publicar aplicativo na Play Store",
                dueDate = "2024-09-15",
                complete = true
            )
        )

        val token = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.TOKEN)
        val personKey = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.PERSON_KEY)

        RetrofitClient.addHeaders(token, personKey)

        viewModelScope.launch {
            try {
                val response = repository.fetchAllTasks()
                _tasks.value = mockTasks
            } catch (e: Exception) {
                val error = e
            }
        }
    }
}