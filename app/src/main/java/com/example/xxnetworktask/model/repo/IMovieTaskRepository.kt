package com.example.xxnetworktask.model.repo

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import io.reactivex.Single

interface IMovieTaskRepository {

    fun getMovieDetails(id: Int): Single<MovieDetailsDataModel>

    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListDataModel>

    fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListDataModel>
}