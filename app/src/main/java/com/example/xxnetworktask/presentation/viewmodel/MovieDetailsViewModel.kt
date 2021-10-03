package com.example.xxnetworktask.presentation.viewmodel

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Single


class MovieDetailsViewModel(private val movieTaskRepository: IMovieTaskRepository) :
    IMovieDetailsViewModel {
    override fun getMovieDetails(id: Int): Single<MovieDetailsResponse> =
        movieTaskRepository.getMovieDetails(id)

    override fun insertMovie(movie: MovieEntity) = movieTaskRepository.insertMovie(movie)

    override fun getMovieWishList(): Single<List<MovieEntity>> =
        movieTaskRepository.getMovieWishList()

}