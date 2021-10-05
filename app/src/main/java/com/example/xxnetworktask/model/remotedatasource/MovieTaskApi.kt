package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTaskApi {

    /**
     * Used to fetch Movie details from server
     * @param id movie id
     * @param api_key api key
     * @param language language
     * @return  Movie details response
     */
    @GET("/3/movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query(value = "api_key") api_key: String,
        @Query(value = "language") language: String
    ): Single<MovieDetailsResponse>


    /**
     * Used to fetch Movie list with search query
     * @param api_key api key
     * @param query search query
     * @param page page
     * @return  Movie list
     */
    @GET("/3/search/movie")
    fun getMovieListBySearchQuery(
        @Query(value = "api_key") api_key: String,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int
    ): Single<MovieListResponse>


    /**
     * Used to fetch Movie list with selected genre
     * @param api_key api key
     * @param with_genres genre id
     * @param page page
     * @return  Movie list
     */
    @GET("/3/discover/movie")
    fun getMovieListByGenre(
        @Query(value = "api_key") api_key: String,
        @Query(value = "with_genres") genreId: Int,
        @Query(value = "page") page: Int
    ): Single<MovieListResponse>
}
