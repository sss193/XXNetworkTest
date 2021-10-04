package com.example.xxnetworktask.model.datamodel

import com.google.gson.annotations.SerializedName


data class MovieDetailsResponse(
    @SerializedName("id")
    val _id: Int,

    @SerializedName("original_title")
    val _title: String,

    @SerializedName("poster_path")
    val _poster: String,

    @SerializedName("overview")
    val _description: String?,

    @SerializedName("vote_average")
    val _rating: Double?

)

