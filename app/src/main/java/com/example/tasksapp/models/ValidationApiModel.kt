package com.example.tasksapp.models

data class ValidationApiModel<T> (
    val data: T?,
    override val success: Boolean,
    override val msg: String? = null
): ValidationModel(success, msg)