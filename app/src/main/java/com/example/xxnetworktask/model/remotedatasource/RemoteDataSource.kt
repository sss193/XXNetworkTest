package com.example.xxnetworktask.model.remotedatasource


import com.example.xxnetworktask.common.AppConstant.Companion.API_KEY
import com.example.xxnetworktask.common.AppConstant.Companion.LANGUAGE_CODE
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class RemoteDataSource(private val movieTaskApi: MovieTaskApi) : IRemoteDataSource {

    override fun getMovieDetails(id: Int): Single<MovieDetailsResponse> =
        movieTaskApi.getMovieDetails(id, API_KEY, LANGUAGE_CODE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListResponse> =
        movieTaskApi.getMovieListBySearchQuery(API_KEY, queryText, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse> =
        movieTaskApi.getMovieListByGenre(API_KEY, genreId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
