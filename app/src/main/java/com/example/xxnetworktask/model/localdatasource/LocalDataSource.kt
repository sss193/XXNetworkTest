package com.example.xxnetworktask.model.localdatasource

import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class LocalDataSource(private val movieDAO: MovieDAO, private val exec: Executor) :
    ILocalDataSource {
    override fun getMovieWishList(): Single<List<MovieEntity>> = movieDAO.getMovieList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getMovieById(movieId: Int): Maybe<MovieEntity> = movieDAO.getMovieById(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteMovieById(movieId: Int) = exec.execute { movieDAO.deleteMovieById(movieId) }

    override fun insertMovie(movie: MovieEntity) = exec.execute { movieDAO.insertMovie(movie) }

    override fun deleteAllMovie() = exec.execute { movieDAO.deleteAllMovie() }

}