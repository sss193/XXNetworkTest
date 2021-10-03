package com.example.xxnetworktask.di

import com.example.xxnetworktask.model.remotedatasource.IRemoteDataSource
import com.example.xxnetworktask.model.remotedatasource.MovieTaskApi
import com.example.xxnetworktask.model.remotedatasource.RemoteDataSource
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import com.example.xxnetworktask.model.repo.MovieTaskRepository
import com.example.xxnetworktask.presentation.viewmodel.HomeViewModel
import com.example.xxnetworktask.presentation.viewmodel.IHomeViewModel
import dagger.Module
import dagger.Provides


@Module
class MovieTaskModule {

    @Provides
    fun providesMovieTaskRepository(remoteDataSource: IRemoteDataSource): IMovieTaskRepository =
        MovieTaskRepository(remoteDataSource)

    @Provides
    fun providesRemoteDataSource(movieTaskApi: MovieTaskApi): IRemoteDataSource =
        RemoteDataSource(movieTaskApi)

}