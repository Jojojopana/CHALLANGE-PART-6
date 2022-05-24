package com.binar.challange_part5

import android.app.Application
import com.binar.challange_part5.di.*
import com.binar.challange_part5.remote.MovieRepository
import com.binar.challange_part5.remote.MoviesRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    localModule,
                    networkModul,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    moviesRemoteDataSourceModule
                )
            )
        }
    }
}