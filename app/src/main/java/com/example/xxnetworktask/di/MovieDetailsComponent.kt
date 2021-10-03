package com.example.xxnetworktask.di

import com.example.xxnetworktask.presentation.view.MovieDetailsActivity
import dagger.Subcomponent


@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)

}