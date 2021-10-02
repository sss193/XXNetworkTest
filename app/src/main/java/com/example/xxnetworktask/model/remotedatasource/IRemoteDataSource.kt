package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import io.reactivex.Single

interface IRemoteDataSource {
    fun getMovieDetails(id: Int): Single<MovieDetailsDataModel>

    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListDataModel>

    fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListDataModel>
}