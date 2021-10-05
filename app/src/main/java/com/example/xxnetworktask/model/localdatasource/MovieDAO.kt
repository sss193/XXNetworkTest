package com.example.xxnetworktask.model.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface MovieDAO {
    @Query("SELECT * FROM MovieEntity")
    fun getMovieList(): Single<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE movieId = :movieId ")
    fun getMovieById(movieId: Int): Maybe<MovieEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)


    @Query("DELETE FROM MovieEntity")
    fun deleteAllMovie()
}