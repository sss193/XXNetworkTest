package com.example.xxnetworktask.di

import com.example.xxnetworktask.model.localdatasource.ILocalDataSource
import com.example.xxnetworktask.model.localdatasource.LocalDataSource
import com.example.xxnetworktask.model.localdatasource.MovieDAO
import com.example.xxnetworktask.model.remotedatasource.IRemoteDataSource
import com.example.xxnetworktask.model.remotedatasource.MovieTaskApi
import com.example.xxnetworktask.model.remotedatasource.RemoteDataSource
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import com.example.xxnetworktask.model.repo.MovieTaskRepository
import com.example.xxnetworktask.presentation.viewmodel.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor


@Module
class MovieTaskModule {

    @Provides
    fun providesMovieTaskViewModelFactory(
        movieTaskRepository: IMovieTaskRepository
    ): MovieViewModelFactory =
        MovieViewModelFactory(movieTaskRepository)

    @Provides
    fun providesMovieTaskRepository(
        remoteDataSource: IRemoteDataSource,
        localDataSource: ILocalDataSource
    ): IMovieTaskRepository =
        MovieTaskRepository(remoteDataSource, localDataSource)

    @Provides
    fun providesRemoteDataSource(movieTaskApi: MovieTaskApi): IRemoteDataSource =
        RemoteDataSource(movieTaskApi)

    @Provides
    fun providesLocalDataSource(movieDAO: MovieDAO, exec: Executor): ILocalDataSource =
        LocalDataSource(movieDAO, exec)

}