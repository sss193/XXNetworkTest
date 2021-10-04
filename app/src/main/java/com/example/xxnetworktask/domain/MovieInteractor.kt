package com.example.xxnetworktask.domain

import com.example.xxnetworktask.domain.datamodel.MovieDetailsModel
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Single

class MovieInteractor(private val movieTaskRepository: IMovieTaskRepository) : IMovieInteractor {
    override fun getMovieDetails(id: Int): Single<MovieDetailsModel> {
        TODO("Not yet implemented")
    }


}