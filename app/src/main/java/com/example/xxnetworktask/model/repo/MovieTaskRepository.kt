package com.example.xxnetworktask.model.repo

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.model.localdatasource.ILocalDataSource
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import com.example.xxnetworktask.model.remotedatasource.IRemoteDataSource
import io.reactivex.Maybe
import io.reactivex.Single

class MovieTaskRepository(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IMovieTaskRepository {

    override fun getMovieDetails(id: Int): Single<MovieDetailsResponse> =
        remoteDataSource.getMovieDetails(id)

    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListResponse> = remoteDataSource.getMovieListBySearchQuery(queryText, page)

    override fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse> =
        remoteDataSource.getMovieListByGenre(genreId, page)

    override fun getMovieWishList(): Single<List<MovieEntity>> = localDataSource.getMovieWishList()

    override fun getMovieById(movieId: Int): Maybe<MovieEntity> =
        localDataSource.getMovieById(movieId)

    override fun insertMovie(movie: MovieEntity) = localDataSource.insertMovie(movie)

    override fun deleteAllMovie() = localDataSource.deleteAllMovie()

}
