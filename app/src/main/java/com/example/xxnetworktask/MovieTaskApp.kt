package com.example.xxnetworktask

import android.app.Application
import android.content.Context
import com.example.xxnetworktask.di.DaggerMovieTaskComponent
import com.example.xxnetworktask.di.LocalDbModule
import com.example.xxnetworktask.di.MovieTaskComponent
import com.example.xxnetworktask.di.NetworkModule

class MovieTaskApp : Application() {
    private lateinit var movieTaskComponent: MovieTaskComponent

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"

        fun get(context: Context): MovieTaskApp {
            return context.applicationContext as MovieTaskApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        initDaggerMovieTaskComponent()
    }

    private fun initDaggerMovieTaskComponent() {
        movieTaskComponent = DaggerMovieTaskComponent.builder()
            .networkModule(NetworkModule(BASE_URL))
            .localDbModule(LocalDbModule(this))
            .build()

        movieTaskComponent.inject(this)
    }

    fun getMovieTaskComponent(): MovieTaskComponent = movieTaskComponent


}