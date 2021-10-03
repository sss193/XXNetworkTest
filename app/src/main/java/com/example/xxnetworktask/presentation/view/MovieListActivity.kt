package com.example.xxnetworktask.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xxnetworktask.MovieTaskApp
import com.example.xxnetworktask.databinding.ActivityMovieListBinding
import com.example.xxnetworktask.di.MovieListModule
import com.example.xxnetworktask.model.datamodel.MovieListResponse
import com.example.xxnetworktask.presentation.view.adapter.MovieListAdapter
import com.example.xxnetworktask.presentation.viewmodel.IMovieListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieListActivity : AppCompatActivity() {

    @Inject
    lateinit var movieListViewModel: IMovieListViewModel

    private val globalDisposable = CompositeDisposable()

    private var currentPage = 1
    private var totalPage = 1
    private var loading = false
    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var viewBinding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeDagger()
        initRecyclerView()
        subscribeToMovieList()

    }

    private fun initializeDagger() {
        MovieTaskApp.get(this).getMovieTaskComponent().plus(MovieListModule()).inject(this)
    }

    private fun populateUi(movieListResponse: MovieListResponse) {
        //showContentLayout()
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
        subscribeToMovieList()
    }

    private fun subscribeToMovieList() {
        movieListViewModel.getMovieListBySearchQuery("hero", currentPage)
            .doOnSubscribe { globalDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<MovieListResponse>() {
                override fun onSuccess(t: MovieListResponse) {
                    loading = false
                    populateUi(t)
                    Log.e("sss", "onSuccess===>${t._movieList.size}")
                    Log.e("sss", "currentPage===>${t._page}")
                }

                override fun onError(e: Throwable) {
                    Log.e("sss", "onError===> $e")
                    loading = false
                }
            }

            )
    }

    private fun onMovieClick(movieId: Int) {
        Log.e("sss", "Movie Id is ====>$movieId")
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }

    override fun onDestroy() {
        globalDisposable.run {
            if (!isDisposed) dispose()
            clear()
        }
        super.onDestroy()
    }

}