package com.example.xxnetworktask.model.repo

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import com.example.xxnetworktask.model.remotedatasource.IRemoteDataSource
import com.example.xxnetworktask.model.remotedatasource.RemoteDataSource
import io.reactivex.Single

class MovieTaskRepository(private val remoteDataSource: IRemoteDataSource) : IMovieTaskRepository {

    override fun getMovieDetails(id: Int): Single<MovieDetailsDataModel> =
        remoteDataSource.getMovieDetails(id)

    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListDataModel> = remoteDataSource.getMovieListBySearchQuery(queryText, page)

    override fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListDataModel> =
        remoteDataSource.getMovieListByGenre(genreId, page)
}