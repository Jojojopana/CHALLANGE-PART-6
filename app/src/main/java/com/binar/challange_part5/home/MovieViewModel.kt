package com.binar.challange_part5.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.challange_part5.data.model.GetAllMovieResponse
import com.binar.challange_part5.remote.MovieRepository
import com.binar.challange_part5.remote.MoviesRemoteDataSource

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val  movieRecommendation : MutableLiveData<List<GetAllMovieResponse>> by lazy {
        MutableLiveData<List<GetAllMovieResponse>>().also {
            getAllMoviesRecommendation()
        }
    }
    fun getMovieRecommendation(): LiveData<List<GetAllMovieResponse>> {
        return movieRecommendation
    }
    val username: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private fun getAllMoviesRecommendation() {
        repository.getMovies(object : MoviesRemoteDataSource.MovieCAllback{
             override fun onComplete(listResult: List<GetAllMovieResponse>) {
                movieRecommendation.value = listResult
            }
            override fun onError() {
                Log.d("ERROROM","ERROR GAES")
            }
        })
    }
}