package com.binar.challange_part5.profile

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

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    private val user : MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
    val updateValidation : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    fun getUpdateValidation():LiveData<Boolean>{
        return updateValidation
    }
    fun resultUser():LiveData<User>{
        return user
    }
    fun getUsername():LiveData<String>{
        return repository.getUsernameValue().asLiveData()
    }

    fun setUsername(username:String){
        viewModelScope.launch {
            repository.setUsername(username)
        }
    }
    fun logOut(){
        viewModelScope.launch {
            repository.clearDataStore()
        }
    }

    fun getUserData(username:String) {
        val usernameResult = StringBuffer()
        val imageResult     = StringBuffer()
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.getAllData(username = username)
            runBlocking(Dispatchers.Main) {
                result?.let {
                    usernameResult.append(it.userName)
                    imageResult.append(it.imagePath.toString())
                }
                val resultDataUser = User(
                    userName    = usernameResult.toString(),
                    imagePath = imageResult.toString()
                )
                user.value = resultDataUser
            }
        }
    }

    fun updateData(userData: User,username: String){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.updateProfile(
                userData,username
            )
            runBlocking(Dispatchers.Main){
                if (result != 0){
                    updateValidation.postValue(true)
                }
            }
        }
    }
}