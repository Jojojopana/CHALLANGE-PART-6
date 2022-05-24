package com.binar.challange_part5.remote

import com.binar.challange_part5.data.model.GetAllMovieResponse
import com.binar.challange_part5.data.model.MovieModel
import com.binar.challange_part5.data.network.MovieApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRemoteDataSource(private val movieApiService: MovieApiService) {
    @OptIn(DelicateCoroutinesApi::class)
    fun getMovie(movieCAllback: MovieCAllback):List<GetAllMovieResponse>{
        GlobalScope.launch(Dispatchers.IO){
            val response = movieApiService.AllMovie()
            runBlocking(Dispatchers.Main){
                if (response.isSuccessful) {
                    val result = response.body()
//                    val code = response.code()
                    result?.let {
                        movieCAllback.onComplete(it.results) }
                }else{
                    movieCAllback.onError()
                }
            }
        }
    return emptyList()
}
    interface MovieCAllback {
        fun onComplete(listResult : List<GetAllMovieResponse>)
        fun onError()
    }
}