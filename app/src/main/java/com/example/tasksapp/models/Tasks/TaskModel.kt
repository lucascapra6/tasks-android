package com.example.tasksapp.models.Tasks

import com.google.gson.annotations.SerializedName

data class TaskModel (
    @SerializedName("Id")
    val id: String,

    @SerializedName("PriorityId")
    val priorityId: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("DueDate")
    val dueDate: String,

    @SerializedName("Complete")
    val complete: Boolean
)
