package com.binar.challange_part5

import android.app.Application
import com.binar.challange_part5.remote.MovieRepository
import com.binar.challange_part5.remote.MoviesRemoteDataSource

class MyApplication: Application() {
    private val remoteDataSource by lazy {
        MoviesRemoteDataSource()
    }
    val repository by lazy {
        MovieRepository(remoteDataSource)
    }
}