package com.binar.challange_part5.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.data.local.userauth.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class LoginViewModel(private val repository: UserRepository): ViewModel() {
   val loginValidation: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result():LiveData<Boolean>{
        return loginValidation
    }
    fun reset(){
        loginValidation.value=false
    }

    fun authLogin(username:String,password:String){
        val UsernameResult = StringBuffer()
        val passwordResult = StringBuffer()
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.authLogin(username,password)
            runBlocking(Dispatchers.Main) {
                if(result!=null){
                    result.let {
                        passwordResult.append(it.password.toString())
                        UsernameResult.append((it.userName))
                    }
                    if(username == UsernameResult.toString() && password == passwordResult.toString()){
                        loginValidation.value = true
                        viewModelScope.launch {
                            repository.setUsername(UsernameResult.toString())
                        }
                    }
                } else {
                    loginValidation.value = false
                }
            }
        }
    }
}

