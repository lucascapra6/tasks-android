package com.example.tasksapp.models.Auth

data class UserModel(
    val token: String,
    val personKey: String,
    val name: String
)