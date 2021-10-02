package com.example.xxnetworktask.model.datamodel

import com.google.gson.annotations.SerializedName


data class MovieDetailsDataModel(
    @SerializedName("id")
    val _id: Int,

    @SerializedName("original_title")
    val _title: String,

    @SerializedName("overview")
    val _description: String,

    @SerializedName("vote_average")
    val _rating: Double,

    @SerializedName("poster_path")
    val _poster: String
)

