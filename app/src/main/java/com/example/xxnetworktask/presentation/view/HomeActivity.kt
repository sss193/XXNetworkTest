package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.xxnetworktask.R
import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.model.repo.MovieTaskRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        MovieTaskRepository().getMovieDetails(44004)
            .doOnSubscribe {}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieDetailsDataModel>() {
                override fun onSuccess(t: MovieDetailsDataModel) {

                    Log.e("sss", "got Result")
                    Log.e("sss", " Result===>${t._title}")

                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "got error-->$e")
                }
            }

            )
    }
}