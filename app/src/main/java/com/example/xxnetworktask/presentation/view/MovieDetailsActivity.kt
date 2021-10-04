package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailsViewModel: IMovieDetailsViewModel

    private var isInWishList: Boolean = false

    private lateinit var viewBinding: ActivityMovieDetailsBinding
    private val globalDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeDagger()
        val movieId = intent.getIntExtra("movieId", 0)
        subscribeToMovieList(movieId)
        subscribeTocheackWishlist(movieId)
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
            //btnTest.setOnClickListener {  }
        }

    }

    private fun subscribeTocheackWishlist(_id: Int) {
        movieDetailsViewModel.getMovieById(_id)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableMaybeObserver<MovieEntity>() {
                override fun onSuccess(t: MovieEntity) {
                    isInWishList = true
                    Glide.with(this@MovieDetailsActivity)
                        .load(R.drawable.ic_wish_list)
                        .into(viewBinding.imgWishList)
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                }

                override fun onComplete() {
                    isInWishList = false
                    Glide.with(this@MovieDetailsActivity)
                        .load(R.drawable.ic_wish_list_not)
                        .into(viewBinding.imgWishList)
                }
            })

        /*    movieDetailsViewModel.getMovieWishList()
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

                )*/
    }

    private fun addwishList(movieDetailsResponse: MovieDetailsResponse) {
        if (isInWishList.not()) {
            var movieEntity = MovieEntity().apply {
                movieId = movieDetailsResponse._id
                movieName = movieDetailsResponse._title
                movieImage = movieDetailsResponse._poster
            }
            movieDetailsViewModel.insertMovie(movieEntity)
            subscribeTocheackWishlist(movieDetailsResponse._id)
            Log.e("sss", "movie Inserted===>")
        } else {
            Toast.makeText(this, "Alreay in wish list", Toast.LENGTH_SHORT).show()
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