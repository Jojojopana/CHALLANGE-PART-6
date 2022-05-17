//package com.binar.challange_part5.register
//
//import android.app.Application
//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.binar.challange_part5.User
//import com.binar.challange_part5.dao.userDB
//import java.util.concurrent.Executors
//import java.util.logging.Logger
//
//class UserViewModel(app: Application): AndroidViewModel(app){
//    val Username: MutableLiveData<String> by lazy {MutableLiveData<String>()}
//    val nama: MutableLiveData<String> by lazy {MutableLiveData<String>()}
//    val messageHandler = Handler(Looper.getMainLooper())
//    private val sharedPreffile = "apppreference"
//    private var mDB: userDB?=null
//    private val context by lazy { getApplication<Application>().applicationContext }
//    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
//        sharedPreffile,
//        Context.MODE_PRIVATE
//    )
//    val executor = Executors.newSingleThreadExecutor()
//    val editor: SharedPreferences.Editor = sharedPreferences.edit()
//    val validation: MutableLiveData<Int> by lazy {MutableLiveData<Int>()}
//    val registervalidation: MutableLiveData<Int> by lazy {MutableLiveData<Int>()}
//    var run = Handler(Looper.getMainLooper())
//    private val _validation : MutableLiveData<Int> by lazy { MutableLiveData<Int>()}
//
//
//    fun runOnUiThread(action: Runnable){
//        run.post(action)
//    }
//
//    fun registerUser(user: User, confirmPassword: String){
//        mDB = userDB.getInstance(context)
//            executor.execute{
//                Log.d("auth","test")
//                val result = mDB?.UserDao()?.addUser(user)
//                runOnUiThread() {
//                    if (result != 0.toLong()) {
//                        Toast.makeText(context, "REGISTER SUKSES", Toast.LENGTH_LONG).show()
//                        registervalidation.postValue(1)
//                    } else {
//                        Toast.makeText(context, "REGISTER GAGAL", Toast.LENGTH_LONG).show()
//                        registervalidation.postValue(0)
//                    }
//                }
//            }
//    }
//
//    fun loginUser(username: String, password: String){
//        mDB = userDB.getInstance(context)
//        executor.execute {
//            val result = mDB?.UserDao()?.login(username,password)
//            runOnUiThread(){
//                if (result?.userName == username && result.password == password){
//                    Toast.makeText(context, "LOGIN BERHASIL", Toast.LENGTH_LONG).show()
//                    validation.postValue(1)
//                    editor.putString("usernamekey",result.userName)
//                    editor.putString("passwordkey",result.password)
//                    editor.apply()
//                    nama.value = result.userName
//                    Log.d("HALO","${Username.value}")
//                } else{
//                Toast.makeText(context,"LOGIN GAGAL", Toast.LENGTH_LONG).show()
//                    validation.postValue(0)
//                }
//            }
//        }
//    }
//
//    fun logincheck(){
//        val usernamevalue = sharedPreferences.getString("usernamekey","default")
//        if (usernamevalue != "default"){
//            Username.value = usernamevalue
//
//            validation.value = 1
//        } else {
//            validation.value = 0
//        }
//        Log.d("debug",usernamevalue.toString())
//        Log.d("login",Username.value.toString())
//    }
//
//    fun setUsername(){
//        val usernamevalue = sharedPreferences.getString("usernamekey","default")
//        nama.value = usernamevalue
//        Log.d("namevalue", Username.value.toString())
//    }
//
//    fun logout(){
//        editor.clear()
//        editor.apply()
//        _validation.postValue(0)
//    }
//
//    fun updateUserData(user:User){
//        mDB = userDB.getInstance(context)
//        executor.execute {
//            val result = mDB?.UserDao()?.updateData(
//                username = user.userName,
//                USERname = nama.value
//            )
//            runOnUiThread {
//                if (result != 0){
//                    Toast.makeText(context,"Update sukses",Toast.LENGTH_SHORT).show()
//                    getUserData()
//                    editor.apply()
//                }else{
//                    Toast.makeText(context,"Update Failed",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }
//    }
//
//    fun getUserData() {
//        val usernameResult = StringBuffer()
//        mDB = userDB.getInstance(context)
//        val password = sharedPreferences.getString("passwordkey","default")
//        executor.execute {
//            val result = mDB?.UserDao()?.getAllData(password)
//            runOnUiThread {
//                result?.forEach(){
//                    usernameResult.append(it.userName)
//                }
//                editor.putString("usernamekey",usernameResult.toString())
//                nama.value = usernameResult.toString()
//            }
//        }
//    }
//}
//
