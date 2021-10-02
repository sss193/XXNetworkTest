package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTaskApi {

    @GET("/3/movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query(value = "api_key") api_key: String,
        @Query(value = "language") language: String,

        ): Single<MovieDetailsDataModel>
}
