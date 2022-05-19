package com.binar.challange_part5.dao

import androidx.room.*
import com.binar.challange_part5.User
import retrofit2.http.DELETE

@Dao
interface userDao {
    @Insert
    fun addUser(user: User): Long

    @Query("Select id From User Where username=(:username)")
    fun getId(username:String) : Int

    @Query("UPDATE User SET username= :username,imagePath = :imagepath WHERE username = :USERname")
    fun updateData(username: String?=null, USERname: String?= null, imagepath: String?= null): Int

    @Query("SELECT * FROM User WHERE username = :userName AND password = :password")
    fun login(userName: String?= null, password: String?= null): User

    @Query("SELECT * FROM User WHERE password = :password")
    fun getAllData(password: String?): User

}