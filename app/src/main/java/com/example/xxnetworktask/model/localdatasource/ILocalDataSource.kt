package com.example.xxnetworktask.model.localdatasource

import io.reactivex.Maybe
import io.reactivex.Single


interface ILocalDataSource {
    fun getMovieWishList(): Single<List<MovieEntity>>
    fun getMovieById(movieId: Int): Maybe<MovieEntity>
    fun deleteMovieById(movieId: Int)
    fun insertMovie(movie: MovieEntity)
    fun deleteAllMovie()
}