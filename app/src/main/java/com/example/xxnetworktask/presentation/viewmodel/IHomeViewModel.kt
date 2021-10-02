package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import io.reactivex.Single

interface IHomeViewModel {
    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListDataModel>
}