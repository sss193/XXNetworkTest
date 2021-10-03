package com.example.xxnetworktask.di

import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import com.example.xxnetworktask.presentation.viewmodel.IMovieListViewModel
import com.example.xxnetworktask.presentation.viewmodel.MovieListViewModel
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {
    @Provides
    fun providesMovieListViewModel(movieTaskRepository: IMovieTaskRepository): IMovieListViewModel =
        MovieListViewModel(movieTaskRepository)
}