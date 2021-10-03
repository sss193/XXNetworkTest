package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.R
import com.example.xxnetworktask.databinding.ActivityMovieDetailsBinding
import com.example.xxnetworktask.di.MovieDetailsModule
import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import com.example.xxnetworktask.presentation.view.adapter.MovieListAdapter
import com.example.xxnetworktask.presentation.viewmodel.IMovieDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailsViewModel: IMovieDetailsViewModel


    private lateinit var viewBinding: ActivityMovieDetailsBinding
    private val globalDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeDagger()
        val movieId = intent.getIntExtra("movieId", 0)
        subscribeToMovieList(movieId)
    }

    private fun subscribeToMovieList(movieId: Int) {
        movieDetailsViewModel.getMovieDetails(movieId)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieDetailsDataModel>() {
                override fun onSuccess(t: MovieDetailsDataModel) {
                    populateUi(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                }
            }

            )
    }

    private fun populateUi(movieDetailsDataModel: MovieDetailsDataModel) {
        viewBinding.apply {
            tvMovieTitle.text = movieDetailsDataModel._title
            tvMovieDes.text = movieDetailsDataModel._description
            tvMovieRating.text = movieDetailsDataModel._rating.toString()
            Glide.with(this@MovieDetailsActivity)
                .load(MovieListAdapter.POSTER_BASE_URL + movieDetailsDataModel._poster)
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_not_found)
                .into(imgPoster)
        }

    }

    override fun onDestroy() {
        globalDisposable.run {
            if (!isDisposed) dispose()
            clear()
        }
        super.onDestroy()
    }

    private fun initializeDagger() {
        MovieTaskApp.get(this).getMovieTaskComponent().plus(MovieDetailsModule()).inject(this)
    }
}