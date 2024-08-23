package com.example.tasksapp.viewModel;
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData;
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper
import com.example.tasksapp.services.models.Auth.UserModel
import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.services.models.ValidateApiModel
import com.example.tasksapp.services.repository.remote.auth.AuthRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesTasksHelper = SharedPreferencesTasksHelper(application)
    private val authRepository = AuthRepository()
    private val _isLoginSuccessful = MutableLiveData<ValidateApiModel>()
    val loginResult: MutableLiveData<ValidateApiModel> = _isLoginSuccessful
    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: MutableLiveData<Boolean> = _isUserLogged

    fun doLogin(email: String, password: String) {
        authRepository.login(email, password, object: ApiListener<UserModel> {
            override fun onSuccess(response: UserModel) {
                _isLoginSuccessful.value = ValidateApiModel(true, "Login bem-sucedido!")
                val token = response.token
                val name = response.name
                val personKey = response.personKey

                sharedPreferencesTasksHelper.store("token", token)
                sharedPreferencesTasksHelper.store("name", name)
                sharedPreferencesTasksHelper.store("personKey", personKey)
            }

             override fun onFailure(t: Throwable) {
                _isLoginSuccessful.value = ValidateApiModel(false, t.message)
            }
        })
    }

    fun handleLoggedSession() {
        // verificar se existe token no shared preferences
        // caso exista, direcionar para a activity principal
        val hasToken = sharedPreferencesTasksHelper.get("token") !== ""
        if(hasToken) {
            isUserLogged.value = true
        }
    }
}
