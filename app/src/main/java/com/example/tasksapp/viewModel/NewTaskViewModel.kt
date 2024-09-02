package com.example.tasksapp.viewModel

import android.app.Application
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasksapp.R
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.models.ValidationApiModel
import com.example.tasksapp.services.repository.remote.tasks.TasksRepository
import kotlinx.coroutines.launch

class NewTaskViewModel(application: Application): AbstractViewModel(application) {
    private val taskRepository = TasksRepository()
    private val _taskResult = MutableLiveData<ValidationApiModel<Boolean>>()
    val taskResult: LiveData<ValidationApiModel<Boolean>> = _taskResult
    private val _isSaveTaskLoading = MutableLiveData<Boolean>()
    val isSaveTaskLoading: LiveData<Boolean> = _isSaveTaskLoading
    fun saveTask(newTask: TaskModel) {
        viewModelScope.launch {
            try {
                _isSaveTaskLoading.value = true
                val response = taskRepository.createTasks(newTask)
                _taskResult.value = ValidationApiModel(response, true, stringfyResource(R.string.task_insert_successful))
            } catch (e: Exception) {
                _taskResult.value = ValidationApiModel(null, false, stringfyResource(R.string.task_insert_failure))
            } finally {
                _isSaveTaskLoading.value = false
            }
        }
    }
}