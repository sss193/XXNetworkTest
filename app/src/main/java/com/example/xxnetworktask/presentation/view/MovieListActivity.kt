package com.example.xxnetworktask.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.common.AppConstant.Companion.ACTIVITY_ROLE
import com.example.xxnetworktask.common.AppConstant.Companion.MOVIE_ID
import com.example.xxnetworktask.common.AppConstant.Companion.SEARCH_LIST
import com.example.xxnetworktask.common.AppConstant.Companion.SEARCH_QUERY
import com.example.xxnetworktask.common.AppConstant.Companion.WISH_LIST
import com.example.xxnetworktask.common.BaseActivity
import com.example.xxnetworktask.databinding.ActivityMovieListBinding
import com.example.xxnetworktask.di.MovieListModule
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.presentation.view.adapter.MovieListAdapter
import com.example.xxnetworktask.presentation.viewmodel.MovieListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    @Inject
    lateinit var movieListViewModel: MovieListViewModel

    private var currentPage = 1
    private var totalPage = 1
    private var loading = false
    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var viewBinding: ActivityMovieListBinding
    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeDagger()
        initRecyclerView()
        getIntentList()
    }

    private fun getIntentList() {

        when (intent.getStringExtra(ACTIVITY_ROLE)) {
            SEARCH_LIST -> {
                searchQuery = intent.getStringExtra(SEARCH_QUERY).toString()
                subscribeToMovieList(searchQuery)
            }
            WISH_LIST -> {
                subscribeToWishList()
            }
        }

    }

    private fun subscribeToWishList() {
        showProgressLayout()
        movieListViewModel.getMovieWishList()
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieListResponse>() {
                override fun onSuccess(t: MovieListResponse) {
                    loading = false
                    populateUi(t)
                }

                override fun onError(e: Throwable) {
                    showErrorLayout()
                    loading = false
                }
            }

            )
    }

    private fun initializeDagger() {
        MovieTaskApp.get(this).getMovieTaskComponent().plus(MovieListModule(this)).inject(this)
    }

    private fun populateUi(movieListResponse: MovieListResponse) {
        if (movieListResponse._movieList.isEmpty())
            showEmptyLayout()
        else
            showContentLayout()

        totalPage = movieListResponse._totalPages
        currentPage = movieListResponse._page

        movieListAdapter.setMovieListResponse(movieListResponse._movieList)
    }

    private fun initRecyclerView() {
        movieListAdapter = MovieListAdapter(this, ::onMovieClick)
        val layoutManager = LinearLayoutManager(this)
        with(viewBinding.rvMovieList) {
            this.layoutManager = layoutManager
            adapter = movieListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MovieListActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        setScrollListener(layoutManager)
    }

    private fun setScrollListener(layoutManager: LinearLayoutManager) {
        viewBinding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && shouldScroll(
                        layoutManager.childCount,
                        layoutManager.findFirstVisibleItemPosition(),
                        layoutManager.itemCount
                    )
                ) {
                    loadMore()
                }
            }
        })
    }

    private fun shouldScroll(
        visibleItemCount: Int,
        firstVisibleItems: Int,
        totalItemCount: Int
    ): Boolean = currentPage < totalPage && loading.not()
            && (visibleItemCount + firstVisibleItems >= totalItemCount) && firstVisibleItems >= 0

    private fun loadMore() {
        currentPage++
        subscribeToMovieList(searchQuery)
    }

    private fun subscribeToMovieList(searchQuery: String?) {
        showProgressLayout()
        movieListViewModel.getMovieListBySearchQuery(searchQuery!!, currentPage)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieListResponse>() {
                override fun onSuccess(t: MovieListResponse) {
                    loading = false
                    populateUi(t)
                }

                override fun onError(e: Throwable) {
                    showErrorLayout()
                    loading = false
                }
            }

            )
    }

    private fun onMovieClick(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, movieId)
        startActivity(intent)
    }

    private fun showContentLayout() {
        viewBinding.rvMovieList.visibility = VISIBLE
        viewBinding.pgLoadingView.visibility = GONE
        viewBinding.errorView.errorLayout.visibility = GONE
        viewBinding.emptyView.emptyLayout.visibility = GONE
    }

    private fun showProgressLayout() {
        viewBinding.rvMovieList.visibility = GONE
        viewBinding.pgLoadingView.visibility = VISIBLE
        viewBinding.errorView.errorLayout.visibility = GONE
        viewBinding.emptyView.emptyLayout.visibility = GONE
    }

    private fun showErrorLayout() {
        viewBinding.rvMovieList.visibility = GONE
        viewBinding.pgLoadingView.visibility = GONE
        viewBinding.errorView.errorLayout.visibility = VISIBLE
        viewBinding.emptyView.emptyLayout.visibility = GONE
    }

    private fun showEmptyLayout() {
        viewBinding.rvMovieList.visibility = GONE
        viewBinding.pgLoadingView.visibility = GONE
        viewBinding.errorView.errorLayout.visibility = GONE
        viewBinding.emptyView.emptyLayout.visibility = VISIBLE
    }
}