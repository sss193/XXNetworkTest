package com.example.xxnetworktask.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.xxnetworktask.presentation.view.MovieDetailsActivity
import com.example.xxnetworktask.presentation.view.MovieListActivity
import com.example.xxnetworktask.presentation.viewmodel.MovieDetailsViewModel
import com.example.xxnetworktask.presentation.viewmodel.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule(private val activity: AppCompatActivity) {

    @Provides
    fun providesMovieDetailsViewModel(movieViewModelFactory: MovieViewModelFactory): MovieDetailsViewModel =
        ViewModelProviders.of(activity, movieViewModelFactory)
            .get(MovieDetailsViewModel::class.java)

}