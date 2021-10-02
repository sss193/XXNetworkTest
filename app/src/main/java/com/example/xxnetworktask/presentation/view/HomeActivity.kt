package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.xxnetworktask.R
import com.example.xxnetworktask.di.DaggerMovieTaskComponent
import com.example.xxnetworktask.di.NetworkModule
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import com.example.xxnetworktask.presentation.viewmodel.IHomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeViewModel: IHomeViewModel

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }

    val globalDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        initializeDagger()

        homeViewModel.getMovieListBySearchQuery("hero", 1)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieListDataModel>() {
                override fun onSuccess(t: MovieListDataModel) {
                    Log.e("sss", "onSuccess===>${t._movieList.size}")
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                }
            }

            )
    }

    override fun onDestroy() {
        globalDisposable.run {
            if (!isDisposed) dispose()
            clear()
        }
        super.onDestroy()
    }

    private fun initializeDagger() {
        DaggerMovieTaskComponent.builder()
            .networkModule(NetworkModule(BASE_URL))
            .build().inject(this)
    }
}