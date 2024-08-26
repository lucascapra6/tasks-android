package com.example.tasksapp.models

open class ValidationModel(
    open val success: Boolean,
    open val msg: String? = null
)