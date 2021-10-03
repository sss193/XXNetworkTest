package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import io.reactivex.Single

interface IMovieDetailsViewModel {
    fun getMovieDetails(id: Int): Single<MovieDetailsDataModel>
}