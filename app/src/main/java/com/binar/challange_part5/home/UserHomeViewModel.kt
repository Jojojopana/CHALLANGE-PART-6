package com.binar.challange_part5.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.challange_part5.data.local.userauth.UserRepository

class UserHomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsername(): LiveData<String> {
        return userRepository.getUsernameValue().asLiveData()
    }
}