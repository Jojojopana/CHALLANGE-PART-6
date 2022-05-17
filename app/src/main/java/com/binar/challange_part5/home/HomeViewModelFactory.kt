package com.binar.challange_part5.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.challange_part5.remote.MovieRepository
import java.lang.IllegalArgumentException

class HomeViewModelFactory constructor(private val movieRepository: MovieRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Error View Model")
    }
}