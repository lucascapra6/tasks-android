package com.example.tasksapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasksapp.R
import com.example.tasksapp.models.Auth.NewUserRegisterModel
import com.example.tasksapp.models.Auth.UserModel
import com.example.tasksapp.models.ValidateApiModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.services.repository.remote.auth.AuthRepository
import com.example.tasksapp.utils.Constants

class RegisterViewModel(application: Application): AbstractViewModel(application) {
    private val authRepository = AuthRepository()
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val _createdUserSuccess = MutableLiveData<ValidateApiModel>()
    val createdUserSuccess: LiveData<ValidateApiModel> = _createdUserSuccess
    fun registerUser(newUser: NewUserRegisterModel) {
        authRepository.register(newUser, object: ApiListener<UserModel> {
            override fun onSuccess(response: UserModel) {
                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.NAME, response.name)
                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.PERSON_KEY, response.personKey)
                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.TOKEN, response.token)

                _createdUserSuccess.value = ValidateApiModel(true, stringfyResource(R.string.success_user_created))
            }

            override fun onFailure(t: Throwable) {
                _createdUserSuccess.value = ValidateApiModel(false, t.message)
            }
        })
    }
}