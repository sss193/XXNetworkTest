package com.example.xxnetworktask.model.repo

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.remotedatasource.RemoteDataSource
import io.reactivex.Single

class MovieTaskRepository {
    val remoteDataSource = RemoteDataSource()

    fun getMovieDetails(id: Int): Single<MovieDetailsDataModel> {
        return remoteDataSource.getMovieDetails(id)
    }
}