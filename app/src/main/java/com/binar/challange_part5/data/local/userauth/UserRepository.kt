package com.binar.challange_part5.data.local.userauth

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.dao.userDao
import androidx.datastore.preferences.preferencesDataStore
import com.binar.challange_part5.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val UserDao: userDao, private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: UserRepository? = null
        fun getInstance(context: Context): UserRepository? {
            return instance ?: synchronized(UserRepository::class.java){
                if(instance == null){
                    val database = userDB.getInstance(context)
                    instance = UserRepository(database!!.UserDao(),context)
                }
                return instance
            }
        }
        private const val DATASTORE_NAME = "application_preferences"

        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")

        private val Context.prefDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    suspend fun registerUser(user:User): Long {
        return UserDao.addUser(user)
    }

    suspend fun authLogin(username: String, password: String): User {
        return UserDao.login(username,password)
    }

    suspend fun updateProfile(user: User, username: String): Int {
        return UserDao.updateData(
            username = user.userName
        )
    }

    suspend fun getAllData(password: String):List<User> {
        return UserDao.getAllData(password)
    }

    suspend fun setUsername(username: String){
        context.prefDataStore.edit {
            it[USERNAME_KEY]=username
        }
    }

    fun getUsernameValue(): Flow<String> {
        return context.prefDataStore.data.map {
            it[USERNAME_KEY]?: "default"
        }
    }

    suspend fun clearDataStore() {
        context.prefDataStore.edit {
            it.clear()
        }
    }
}