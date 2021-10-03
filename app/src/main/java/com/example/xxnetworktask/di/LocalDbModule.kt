package com.example.xxnetworktask.di

import android.content.Context
import androidx.room.Room
import com.example.xxnetworktask.model.localdatasource.MovieDAO
import com.example.xxnetworktask.model.localdatasource.MovieDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class LocalDbModule(private val context: Context) {
    @Provides
    fun providesCouponDatabase(): MovieDatabase =
        Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movies.db")
            .build()

    @Provides
    fun providesCouponDAO(movieDatabase: MovieDatabase): MovieDAO {
        return movieDatabase.movieDAO()
    }


    @Provides
    fun providesExecutor(): Executor {
        return Executors.newFixedThreadPool(2)
    }

}