package com.example.xxnetworktask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.model.repo.IMovieTaskRepository
import io.reactivex.Single

class HomeViewModel(private val movieTaskRepository: IMovieTaskRepository) : ViewModel() {

    fun working() {
        Log.e("sss", "working---->")
    }

}