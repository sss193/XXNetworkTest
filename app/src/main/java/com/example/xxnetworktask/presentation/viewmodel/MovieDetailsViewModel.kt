package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Single


class MovieDetailsViewModel(private val movieTaskRepository: IMovieTaskRepository) :
    IMovieDetailsViewModel {
    override fun getMovieDetails(id: Int): Single<MovieDetailsDataModel> =
        movieTaskRepository.getMovieDetails(id)
}