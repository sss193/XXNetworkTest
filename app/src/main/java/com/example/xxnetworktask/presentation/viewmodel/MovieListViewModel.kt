package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Single


class MovieListViewModel(private val movieTaskRepository: IMovieTaskRepository) :
    IMovieListViewModel {
    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListResponse> = movieTaskRepository.getMovieListBySearchQuery(queryText, page)

    override fun getMovieWishList(): Single<MovieListResponse> =
        movieTaskRepository.getMovieWishList()
}