package com.binar.challange_part5.remote

import com.binar.challange_part5.data.model.GetAllMovieResponse
import com.binar.challange_part5.data.model.MovieModel
import com.binar.challange_part5.data.network.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRemoteDataSource {
    fun getMovie(movieCAllback: MovieCAllback):List<GetAllMovieResponse>{
        MovieApi.instance.AllMovie().enqueue(object :
            Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                when {
                    response.isSuccessful -> {
                        response.body()?.results?.let {
                            movieCAllback.onComplete(it)
                        }
                    }
                    response.code() == 401 -> {
                        movieCAllback.onError()
                    }
                    response.code()==404 ->{
                        movieCAllback.onError()
                    }
                }
            }
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                movieCAllback.onError()
            }
        })
        return emptyList()
    }
    interface MovieCAllback {
        fun onComplete(listResult : List<GetAllMovieResponse>)
        fun onError()
    }
}