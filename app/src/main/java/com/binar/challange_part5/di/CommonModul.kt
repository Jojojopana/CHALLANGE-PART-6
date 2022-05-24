package com.binar.challange_part5.di

import androidx.room.Room
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.data.local.userauth.UserRepository
import com.binar.challange_part5.data.network.MovieApiService
import com.binar.challange_part5.remote.MoviesRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array.get

val localModule = module {
    factory { get<userDB>().UserDao()}
    single {
        Room.databaseBuilder(
            androidContext(),
            userDB::class.java,
            "app123.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModul = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MovieApiService::class.java)
    }



}
val moviesRemoteDataSourceModule= module {
    single {
        MoviesRemoteDataSource(get())
    }
}


