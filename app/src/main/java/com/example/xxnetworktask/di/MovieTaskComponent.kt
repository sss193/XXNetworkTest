package com.example.xxnetworktask.di

import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.presentation.view.HomeActivity
import com.example.xxnetworktask.presentation.view.MovieDetailsActivity
import com.example.xxnetworktask.presentation.view.MovieListActivity
import dagger.Component



@Component(modules = [NetworkModule::class, MovieTaskModule::class])
interface MovieTaskComponent {
    fun inject(application: MovieTaskApp)

    fun plus(movieListModule: MovieListModule): MovieListComponent

}