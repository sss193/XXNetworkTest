package com.example.xxnetworktask.presentation.view


import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.R
import com.example.xxnetworktask.common.AppConstant.Companion.MOVIE_ID
import com.example.xxnetworktask.common.BaseActivity
import com.example.xxnetworktask.databinding.ActivityMovieDetailsBinding
import com.example.xxnetworktask.di.MovieDetailsModule
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import com.example.xxnetworktask.model.localdatasource.MovieEntity
import com.example.xxnetworktask.presentation.view.adapter.MovieListAdapter
import com.example.xxnetworktask.presentation.viewmodel.MovieDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieDetailsActivity : BaseActivity() {

    @Inject
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private var isInWishList: Boolean = false

    private lateinit var viewBinding: ActivityMovieDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeDagger()
        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        subscribeToMovieDetails(movieId)
        subscribeTocheackWishlist(movieId)
    }

    private fun subscribeToMovieDetails(movieId: Int) {
        showProgressLayout()
        movieDetailsViewModel.getMovieDetails(movieId)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieDetailsResponse>() {
                override fun onSuccess(t: MovieDetailsResponse) {
                    showContentLayout()
                    populateUi(t)
                }

                override fun onError(e: Throwable) {
                    showErrorLayout()
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
            imgWishList.setOnClickListener { addWishList(movieDetailsResponse) }
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
                    showErrorLayout()
                }

                override fun onComplete() {
                    isInWishList = false
                    Glide.with(this@MovieDetailsActivity)
                        .load(R.drawable.ic_wish_list_not)
                        .into(viewBinding.imgWishList)
                }
            })
    }

    private fun addWishList(movieDetailsResponse: MovieDetailsResponse) {
        if (isInWishList.not()) {
            val movieEntity = MovieEntity().apply {
                movieId = movieDetailsResponse._id
                movieName = movieDetailsResponse._title
                movieImage = movieDetailsResponse._poster
            }
            movieDetailsViewModel.insertMovie(movieEntity)
            subscribeTocheackWishlist(movieDetailsResponse._id)
        } else {
            Toast.makeText(this, "Already in wish list", Toast.LENGTH_SHORT).show()
        }

    }


    private fun initializeDagger() {
        MovieTaskApp.get(this).getMovieTaskComponent().plus(MovieDetailsModule(this)).inject(this)
    }

    private fun showContentLayout() {
        viewBinding.contentView.visibility = VISIBLE
        viewBinding.pgLoadingView.visibility = GONE
        viewBinding.errorView.errorLayout.visibility = GONE
    }

    private fun showProgressLayout() {
        viewBinding.contentView.visibility = GONE
        viewBinding.pgLoadingView.visibility = VISIBLE
        viewBinding.errorView.errorLayout.visibility = GONE
    }

    private fun showErrorLayout() {
        viewBinding.contentView.visibility = VISIBLE
        viewBinding.pgLoadingView.visibility = GONE
        viewBinding.errorView.errorLayout.visibility = GONE
    }
}