package com.binar.challange_part5.di

import android.telecom.Call
import com.binar.challange_part5.data.local.userauth.UserRepository
import com.binar.challange_part5.data.model.DetailMovie
import com.binar.challange_part5.fragment.DetailFragment
import com.binar.challange_part5.home.HomeFragment
import com.binar.challange_part5.home.MovieViewModel
import com.binar.challange_part5.login.LoginViewModel
import com.binar.challange_part5.profile.ProfileViewModel
import com.binar.challange_part5.register.RegisterViewModel
import com.binar.challange_part5.remote.MovieRepository
import com.binar.challange_part5.splash.SplashViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val useCaseModule = module {

}

@DelicateCoroutinesApi
val viewModelModule = module {
    viewModel { MovieViewModel(get(),get())}
    viewModel {LoginViewModel(repository = get())}
    viewModel {ProfileViewModel(get())}
    viewModel {RegisterViewModel(get())}
    viewModel {SplashViewModel(get())}

}

val repositoryModule= module {
    single { UserRepository(get(),androidContext()) }
    single { MovieRepository(get()) }
}

