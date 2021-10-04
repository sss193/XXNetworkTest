package com.example.xxnetworktask.domain

import com.example.xxnetworktask.domain.datamodel.MovieDetailsModel
import io.reactivex.Single

interface IMovieInteractor {
    fun getMovieDetails(id: Int): Single<MovieDetailsModel>
}