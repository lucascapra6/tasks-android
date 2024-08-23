package com.example.tasksapp.viewModel;
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData;
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.repository.remote.auth.AuthRepository
import com.example.tasksapp.utils.Constants

class MainActivityViewModel (application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val authRepository = AuthRepository()
    private val _userName = MutableLiveData<String>()
    var userName: LiveData<String> = _userName

    fun setUserName() {
        val storedUserName = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.NAME)
        _userName.value = storedUserName
    }
}
