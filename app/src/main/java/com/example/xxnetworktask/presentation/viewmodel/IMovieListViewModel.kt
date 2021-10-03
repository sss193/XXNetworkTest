package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single

interface IMovieListViewModel {
    fun getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>
}