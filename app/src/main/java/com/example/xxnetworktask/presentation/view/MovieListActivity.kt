package com.example.xxnetworktask.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xxnetworktask.R
import com.example.xxnetworktask.databinding.ActivityHomeBinding
import com.example.xxnetworktask.databinding.ActivityMovieListBinding
import com.example.xxnetworktask.model.datamodel.MovieListDataModel
import com.example.xxnetworktask.presentation.view.adapter.MovieListAdapter

class MovieListActivity : AppCompatActivity() {
    private var currentPage = 1
    private var totalPage = 1
    private var loading = false
    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var viewBinding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    private fun populateUi(movieListDataModel: MovieListDataModel) {
        //showContentLayout()
        totalPage = movieListDataModel._totalPages
        currentPage = movieListDataModel._page

    }

    private fun initRecyclerView() {
        movieListAdapter = MovieListAdapter(this)
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
                if (dy > 0 && shouldScroll()) {
                    loadMore()
                }
            }
        })
    }

    private fun shouldScroll(): Boolean = currentPage < totalPage && loading.not()

    private fun loadMore() {
        currentPage++
        //         subscribeToMovieList()
    }

}