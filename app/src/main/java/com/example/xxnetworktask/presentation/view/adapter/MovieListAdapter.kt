package com.example.xxnetworktask.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xxnetworktask.R
import com.example.xxnetworktask.databinding.LayoutRowMovieListBinding
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse

class MovieListAdapter(private val context: Context, private val onMovieClick: (Int) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    companion object {
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    private var movieList = mutableListOf<MovieDetailsResponse>()
    private val layoutInflater = LayoutInflater.from(context)

    inner class MovieListViewHolder(val viewBinding: LayoutRowMovieListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

    fun setMovieListResponse(reponse: List<MovieDetailsResponse>) {
        movieList.addAll(reponse)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(LayoutRowMovieListBinding.inflate(layoutInflater, parent, false))


    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.apply {
            viewBinding.apply {
                tvMovieName.text = movieList[position]._title

                Glide.with(context)
                    .load(POSTER_BASE_URL + movieList[position]._poster)
                    .placeholder(R.drawable.ic_load)
                    .error(R.drawable.ic_not_found)
                    .into(imgMoviePoster)

                movieRowLayout.setOnClickListener { onMovieClick(movieList[position]._id) }

            }
        }
    }

    override fun getItemCount(): Int = movieList.size

}