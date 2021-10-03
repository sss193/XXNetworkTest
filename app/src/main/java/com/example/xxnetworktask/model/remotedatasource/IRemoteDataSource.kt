package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single

interface IRemoteDataSource {
    fun getMovieDetails(id: Int): Single<MovieDetailsResponse>

    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>

    fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse>
}