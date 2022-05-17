package com.binar.challange_part5

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.challange_part5.data.local.userauth.UserRepository
import java.lang.RuntimeException

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(UserRepository::class.java)
                .newInstance(UserRepository.getInstance(context))
        } catch (e: InstantiationException) {
            throw RuntimeException("cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("cannot create an instance of $modelClass", e)
        }
    }
}