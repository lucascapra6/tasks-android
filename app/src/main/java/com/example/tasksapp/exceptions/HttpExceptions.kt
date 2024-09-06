package com.example.tasksapp.exceptions

open class HttpExceptions(message: String?) : Exception(message) {
    class ForbiddenException : HttpExceptions("Forbidden") {

    }
}