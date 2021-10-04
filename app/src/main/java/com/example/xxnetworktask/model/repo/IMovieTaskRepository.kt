package com.example.xxnetworktask.model.repo

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import io.reactivex.Maybe
import io.reactivex.Single

interface IMovieTaskRepository {

    fun getMovieDetails(id: Int): Single<MovieDetailsResponse>

    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>

    fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse>

    fun getMovieWishList(): Single<MovieListResponse>
    fun getMovieById(movieId: Int): Maybe<MovieEntity>
    fun insertMovie(movie: MovieEntity)
    fun deleteAllMovie()
}