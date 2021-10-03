package com.example.xxnetworktask.di

import com.example.xxnetworktask.presentation.view.MovieListActivity
import dagger.Subcomponent

@Subcomponent(modules = [MovieListModule::class])
interface MovieListComponent {
    fun inject(movieListActivity: MovieListActivity)

}