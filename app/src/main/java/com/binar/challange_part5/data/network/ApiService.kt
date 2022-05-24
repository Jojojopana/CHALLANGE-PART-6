package com.binar.challange_part5.data.network

import com.binar.challange_part5.data.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET


interface MovieApiService{
    //GET DIGUNAKAN UNTUK MEMANGGIL SEMUA DATA YANG TERDAPAT PADA SERVER
    @GET("3/movie/550/recommendations?api_key=c6e77fa6d3566f19b433a9dd5673542c")
    suspend fun AllMovie():Response<MovieModel>
}
