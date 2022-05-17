package com.binar.challange_part5.data.api

import com.google.gson.annotations.SerializedName
import kotlin.Result

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<com.binar.challange_part5.data.api.Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)