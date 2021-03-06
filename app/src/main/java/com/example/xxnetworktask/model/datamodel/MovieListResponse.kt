package com.example.xxnetworktask.model.datamodel

import com.google.gson.annotations.SerializedName


data class MovieListResponse(
    @SerializedName("page")
    val _page: Int,

    @SerializedName("results")
    val _movieList: List<MovieDetailsResponse>,

    @SerializedName("total_pages")
    val _totalPages: Int,

    @SerializedName("total_results")
    val _totalResults: Int
)