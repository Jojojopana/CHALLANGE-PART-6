package com.binar.challange_part5.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.challange_part5.User

@Database(entities = [User::class], version = 1)
abstract class userDB: RoomDatabase() {
    abstract fun UserDao(): userDao

    companion object{
        private var instance : userDB?=null
        fun getInstance(context: Context): userDB?{
            if(instance==null){
                synchronized(User::class){
                    instance = Room.databaseBuilder(context.applicationContext,userDB::class.java,"app.db").build()
                }
        }
            return instance
        }
}

    fun destroyInstance() {
        instance = null
    }
}
