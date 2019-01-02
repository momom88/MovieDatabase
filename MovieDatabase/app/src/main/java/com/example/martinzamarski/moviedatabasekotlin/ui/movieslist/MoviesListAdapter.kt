package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.martinzamarski.moviedatabasekotlin.util.IMAGEURL
import com.example.martinzamarski.moviedatabasekotlin.databinding.ListItemMoviesActivityBinding
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.ui.MovieInterface
import com.squareup.picasso.Picasso


class MoviesListAdapter (private val mClickRecipeInterface: MovieInterface, private val mMovieList: List<Movie>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMoviesActivityBinding.inflate(inflater)
        binding.clickMovie = mClickRecipeInterface
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = mMovieList[position]
        holder.bind(movie)
        holder.imageView(movie)
    }

    override fun getItemCount(): Int = mMovieList.size

    inner class ViewHolder(var binding: ListItemMoviesActivityBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }

        fun imageView(movie: Movie){
            val widthImageMovie = 750
            val heightImageMovie = 1035
            val urlImage = IMAGEURL + movie.posterPath
            Picasso.get()
                .load(urlImage)
                .resize(widthImageMovie, heightImageMovie)
                .into(binding.imageMovie)
        }
    }
}

