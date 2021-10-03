package com.example.xxnetworktask.domain.datamodel


data class MovieDetailsModel(
    val _id: Int,

    val _title: String,

    val _description: String,

    val _rating: Double,

    val _poster: String,

    val _isWishList: Boolean
)
