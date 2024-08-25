package com.example.tasksapp.models.Tasks

data class TaskModel (
    val id: String,
    val priorityId: String,
    val description: String,
    val dueDate: String,
    val complete: Boolean
)
