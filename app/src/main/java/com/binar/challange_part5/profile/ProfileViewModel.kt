package com.binar.challange_part5.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binar.challange_part5.User
import com.binar.challange_part5.dao.userDB
import java.util.concurrent.Executors

class ProfileViewModel(app: Application): AndroidViewModel(app) {

    val Username: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val nama: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val messageHandler = Handler(Looper.getMainLooper())
    private val sharedPreffile = "apppreference"
    private var mDB: userDB?=null
    private val context by lazy { getApplication<Application>().applicationContext }
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        sharedPreffile,
        Context.MODE_PRIVATE
    )
    val executor = Executors.newSingleThreadExecutor()
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val validation: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val registervalidation: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    var run = Handler(Looper.getMainLooper())
    private val _validation : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun runOnUiThread(action: Runnable){
        run.post(action)
    }

    fun setUsername(){
        val usernamevalue = sharedPreferences.getString("usernamekey","default")
        nama.value = usernamevalue
        Log.d("namevalue", Username.value.toString())
    }

    fun logout(){
        editor.clear()
        editor.apply()
        _validation.postValue(0)
    }

    fun updateUserData(user: User){
        mDB = userDB.getInstance(context)
        executor.execute {
            val result = mDB?.UserDao()?.updateData(
                username = user.userName,
                USERname = nama.value
            )
            runOnUiThread {
                if (result != 0){
                    Toast.makeText(context,"Update sukses", Toast.LENGTH_SHORT).show()
                    getUserData()
                    editor.apply()
                }else{
                    Toast.makeText(context,"Update Failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun getUserData() {
        val usernameResult = StringBuffer()
        mDB = userDB.getInstance(context)
        val password = sharedPreferences.getString("passwordkey","default")
        executor.execute {
            val result = mDB?.UserDao()?.getAllData(password)
            runOnUiThread {
                result?.forEach(){
                    usernameResult.append(it.userName)
                }
                editor.putString("usernamekey",usernameResult.toString())
                nama.value = usernameResult.toString()
            }
        }
    }
}