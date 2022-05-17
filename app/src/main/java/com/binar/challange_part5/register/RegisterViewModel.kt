package com.binar.challange_part5.register

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.binar.challange_part5.User
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.data.local.userauth.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    val registervalidation: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result(): LiveData<Boolean>{
        return registervalidation
    }

    fun reset() {
        registervalidation.postValue(false)
    }

    fun addUser(user: User, confirmPassword: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.registerUser(user)
            runBlocking(Dispatchers.Main) {
                if(result != 0. toLong()){
                    registervalidation.postValue(true)
                }
            }
        }
    }

}