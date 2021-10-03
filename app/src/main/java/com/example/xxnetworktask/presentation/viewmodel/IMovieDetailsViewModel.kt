package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import io.reactivex.Single

interface IMovieDetailsViewModel {
    fun getMovieDetails(id: Int): Single<MovieDetailsResponse>
    fun insertMovie(movie: MovieEntity)

    fun getMovieWishList(): Single<List<MovieEntity>>
}