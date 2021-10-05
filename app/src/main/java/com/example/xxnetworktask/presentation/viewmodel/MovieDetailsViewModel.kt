package com.example.xxnetworktask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Maybe
import io.reactivex.Single


class MovieDetailsViewModel(private val movieTaskRepository: IMovieTaskRepository) :
    ViewModel() {
    fun getMovieDetails(id: Int): Single<MovieDetailsResponse> =
        movieTaskRepository.getMovieDetails(id)

    fun insertMovie(movie: MovieEntity) = movieTaskRepository.insertMovie(movie)

    fun getMovieById(movieId: Int): Maybe<MovieEntity> =
        movieTaskRepository.getMovieById(movieId)

    fun deleteMovieById(movieId: Int) = movieTaskRepository.deleteMovieById(movieId)


}