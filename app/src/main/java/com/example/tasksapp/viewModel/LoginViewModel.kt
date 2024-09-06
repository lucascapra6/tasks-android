package com.example.tasksapp.viewModel;
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData;
import com.example.tasksapp.R
import com.example.tasksapp.infra.BiometricException
import com.example.tasksapp.infra.BiometricsUtils
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.models.Auth.UserModel
import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.models.ValidationModel
import com.example.tasksapp.services.repository.remote.auth.AuthRepository
import com.example.tasksapp.utils.Constants

class LoginViewModel(application: Application) : AbstractViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val authRepository = AuthRepository()
    private val _isLoginSuccessful = MutableLiveData<ValidationModel>()
    val loginResult: LiveData<ValidationModel> = _isLoginSuccessful
    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: LiveData<Boolean> = _isUserLogged

    fun doLogin(email: String, password: String) {
        authRepository.login(email, password, object: ApiListener<UserModel> {
            override fun onSuccess(response: UserModel) {
                _isLoginSuccessful.value = ValidationModel(true, stringfyResource(R.string.success_login))
                val token = response.token
                val name = response.name
                val personKey = response.personKey

                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.TOKEN, token)
                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.NAME, name)
                sharedPreferencesTasksHelper.store(Constants.SharedPreferencesKeys.PERSON_KEY, personKey)
            }

             override fun onFailure(t: Throwable) {
                _isLoginSuccessful.value = ValidationModel(false, t.message)
            }
        })
    }

    fun handleLoggedSession() {
        // verificar se existe token no shared preferences
        // caso exista, direcionar para a activity principal
        val hasToken = sharedPreferencesTasksHelper.get(Constants.SharedPreferencesKeys.TOKEN) !== ""
        if(hasToken) {
            try {
                BiometricsUtils.verifyIsBiometricAvailable(getApplication())
                _isUserLogged.value = true
            } catch (e: BiometricException) {
                when(e) {
                    BiometricException.NoBiometricHardwareException -> {
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    }
                    BiometricException.BiometricUnavailableException -> {
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    }
                    BiometricException.NoBiometricEnrolledException -> {
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    }
                    BiometricException.UnknownBiometricException -> {
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
