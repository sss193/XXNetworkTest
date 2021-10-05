package com.example.xxnetworktask.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.xxnetworktask.presentation.viewmodel.MovieListViewModel
import com.example.xxnetworktask.presentation.viewmodel.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieListModule(private val activity: AppCompatActivity) {

    @Provides
    fun providesMovieDetailsViewModel(movieViewModelFactory: MovieViewModelFactory): MovieListViewModel =
        ViewModelProviders.of(activity, movieViewModelFactory).get(MovieListViewModel::class.java)

}