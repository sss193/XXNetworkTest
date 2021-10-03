package com.example.xxnetworktask.di

import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import com.example.xxnetworktask.presentation.viewmodel.IMovieDetailsViewModel
import com.example.xxnetworktask.presentation.viewmodel.MovieDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun providesMovieDetailsViewModel(movieTaskRepository: IMovieTaskRepository): IMovieDetailsViewModel =
        MovieDetailsViewModel(movieTaskRepository)
}