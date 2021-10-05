package com.example.xxnetworktask.model.remotedatasource



import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class RemoteDataSource(
    private val movieTaskApi: MovieTaskApi,
    private val apiKey: String, private val languageCode: String
) : IRemoteDataSource {

    override fun getMovieDetails(id: Int): Single<MovieDetailsResponse> =
        movieTaskApi.getMovieDetails(id, apiKey, languageCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getMovieListBySearchQuery(
        queryText: String,
        page: Int
    ): Single<MovieListResponse> =
        movieTaskApi.getMovieListBySearchQuery(apiKey, queryText, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse> =
        movieTaskApi.getMovieListByGenre(apiKey, genreId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
