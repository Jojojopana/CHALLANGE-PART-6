package com.binar.challange_part5.remote

import com.binar.challange_part5.data.model.GetAllMovieResponse

class MovieRepository constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource){
    fun getMovies(movieCAllback: MoviesRemoteDataSource.MovieCAllback) : List<GetAllMovieResponse> {
        return moviesRemoteDataSource.getMovie(movieCAllback)
    }
}