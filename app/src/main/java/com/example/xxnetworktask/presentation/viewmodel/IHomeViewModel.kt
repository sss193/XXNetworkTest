package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single

interface IHomeViewModel {
    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>
}