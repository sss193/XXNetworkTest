package com.example.xxnetworktask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xxnetworktask.model.repo.IMovieTaskRepository

class MovieViewModelFactory(val movieTaskRepository: IMovieTaskRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IMovieTaskRepository::class.java)
            .newInstance(movieTaskRepository)
    }
}