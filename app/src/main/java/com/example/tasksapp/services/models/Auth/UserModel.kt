package com.example.tasksapp.services.models.Auth

data class UserModel(
    val token: String,
    val personKey: String,
    val name: String
)