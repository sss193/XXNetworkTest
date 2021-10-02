package com.example.xxnetworktask.model.remotedatasource


import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class RemoteDataSource(private val movieTaskApi: MovieTaskApi) : IRemoteDataSource {

    companion object {
        const val API_KEY = "fd671a05e78db1a26837c3f4226776e3"
        const val LANGUAGE_CODE = "en-US"
    }

    override fun getMovieDetails(id: Int): Single<MovieDetailsDataModel> {
        return movieTaskApi.getMovieDetails(id, API_KEY, LANGUAGE_CODE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListDataModel> {
        return movieTaskApi.getMovieListBySearchQuery(API_KEY, queryText, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListDataModel> {
        return movieTaskApi.getMovieListByGenre(API_KEY, genreId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
