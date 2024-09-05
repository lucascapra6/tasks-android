package com.example.tasksapp.utils

object Constants {

    object Messages {
        const val SNACKBAR_DEFAULT_MESSAGE = "Replace with your own action"
        const val SNACKBAR_ACTION_TEXT = "Action"
        const val GREETING_MESSAGE = "Olá"
        const val LOGIN_SUCCESS = "Login bem-sucedido!"
    }

    object SharedPreferencesKeys {
        const val TOKEN = "token"
        const val NAME = "name"
        const val PERSON_KEY = "personKey"
    }

    object BundleDataKeys {
        const val TASK_ID = "task_id"
    }

    object TaskFilterKey {
        const val TASK_FILTER_PARAM =  "taskFilterParam"
    }

    object TaskFilterValues {
        const val ALL_TASKS = 1
        const val NEXT_7_DAYS = 2
        const val OVERDUE = 3
    }
}