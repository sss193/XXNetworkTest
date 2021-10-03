package com.example.xxnetworktask.di

import com.example.xxnetworktask.presentation.view.MovieDetailsActivity
import com.example.xxnetworktask.presentation.view.MovieListActivity
import dagger.Subcomponent


@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)

}