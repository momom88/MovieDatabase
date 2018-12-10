package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var mMovie: LiveData<List<Movie>>

    //the call to repository method to display the current data according to the user's request
    fun getMovie(sortOrder: Int): LiveData<List<Movie>> {
        mMovie = movieRepository.getMovieList(sortOrder)
        return mMovie
    }
}