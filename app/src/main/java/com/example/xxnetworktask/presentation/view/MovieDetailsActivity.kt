package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.R
import com.example.xxnetworktask.databinding.ActivityMovieDetailsBinding
import com.example.xxnetworktask.di.MovieDetailsModule
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
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
            .subscribe(object : DisposableSingleObserver<MovieDetailsResponse>() {
                override fun onSuccess(t: MovieDetailsResponse) {
                    populateUi(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                }
            }

            )
    }

    private fun populateUi(movieDetailsResponse: MovieDetailsResponse) {
        viewBinding.apply {
            tvMovieTitle.text = movieDetailsResponse._title
            tvMovieDes.text = movieDetailsResponse._description
            tvMovieRating.text = movieDetailsResponse._rating.toString()
            Glide.with(this@MovieDetailsActivity)
                .load(MovieListAdapter.POSTER_BASE_URL + movieDetailsResponse._poster)
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_not_found)
                .into(imgPoster)
            imgWishList.setOnClickListener { addwishList(movieDetailsResponse) }
            btnTest.setOnClickListener { testResult() }
        }

    }

    private fun testResult() {
        movieDetailsViewModel.getMovieWishList()
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<MovieEntity>>() {
                override fun onSuccess(t: List<MovieEntity>) {
                    Log.e("sss", "onSuccess===> ${t.size}")
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                }
            }

            )
    }

    private fun addwishList(movieDetailsResponse: MovieDetailsResponse) {
        var movieEntity = MovieEntity().apply {
            movieId = movieDetailsResponse._id
            movieName = movieDetailsResponse._title
            movieImage = movieDetailsResponse._poster
        }
        movieDetailsViewModel.insertMovie(movieEntity)
        Log.e("sss", "movie Inserted===>")
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