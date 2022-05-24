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

        private const val DATASTORE_NAME = "application_preferences"

        private val USERNAME_KEY = stringPreferencesKey("username_key")

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
            USERname = username,
            username = user.userName,
            imagepath = user.imagePath
        )
    }

    suspend fun getAllData(username: String):User {
        return UserDao.getAllData(username)
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