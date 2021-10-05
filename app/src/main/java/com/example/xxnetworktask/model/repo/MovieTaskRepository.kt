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

    override fun getMovieWishList(): Single<MovieListResponse> =
        localDataSource.getMovieWishList().map { convertMovieListIntoMovieResponse(it) }

    override fun getMovieById(movieId: Int): Maybe<MovieEntity> =
        localDataSource.getMovieById(movieId)

    override fun deleteMovieById(movieId: Int) = localDataSource.deleteMovieById(movieId)

    override fun insertMovie(movie: MovieEntity) = localDataSource.insertMovie(movie)

    override fun deleteAllMovie() = localDataSource.deleteAllMovie()

    private fun convertMovieListIntoMovieResponse(data: List<MovieEntity>): MovieListResponse {
        val movieList: MutableList<MovieDetailsResponse> = ArrayList()
        for (movie in data) {
            val movieDetailResponse =
                MovieDetailsResponse(
                    movie.movieId!!,
                    movie.movieName!!,
                    movie.movieImage!!,
                    null,
                    null
                )
            movieList.add(movieDetailResponse)

        }
        return MovieListResponse(1, movieList, 1, data.size)
    }

}
