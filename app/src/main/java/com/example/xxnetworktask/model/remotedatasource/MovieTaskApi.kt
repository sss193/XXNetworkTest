package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTaskApi {

    @GET("/3/movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query(value = "api_key") api_key: String,
        @Query(value = "language") language: String
    ): Single<MovieDetailsDataModel>

    @GET("/3/search/movie")
    fun getMovieListBySearchQuery(
        @Query(value = "api_key") api_key: String,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int
    ): Single<MovieListDataModel>


    @GET("/3/discover/movie")
    fun getMovieListByGenre(
        @Query(value = "api_key") api_key: String,
        @Query(value = "with_genres") genreId: Int,
        @Query(value = "page") page: Int
    ): Single<MovieListDataModel>
}
