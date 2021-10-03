package com.example.xxnetworktask.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xxnetworktask.databinding.LayoutRowMovieListBinding
import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel

class MovieListAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private var movieList = mutableListOf<MovieDetailsDataModel>()
    private val layoutInflater = LayoutInflater.from(context)

    inner class MovieListViewHolder(val viewBinding: LayoutRowMovieListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

    fun setMovieListResponse(reponse: List<MovieDetailsDataModel>) {
        movieList.addAll(reponse)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(LayoutRowMovieListBinding.inflate(layoutInflater, parent, false))


    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.apply {
            viewBinding.apply {
                tvMovieName.text = movieList[position]._title
            }
        }
    }

    override fun getItemCount(): Int = movieList.size

}