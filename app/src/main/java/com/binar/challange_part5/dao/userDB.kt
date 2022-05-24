package com.binar.challange_part5.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.challange_part5.User

@Database(entities = [User::class], version = 1)
abstract class userDB: RoomDatabase() {
    abstract fun UserDao(): userDao
}
