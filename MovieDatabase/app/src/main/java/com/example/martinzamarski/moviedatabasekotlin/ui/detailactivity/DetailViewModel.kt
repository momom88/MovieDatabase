package com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import javax.inject.Inject


class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var mReviews: LiveData<List<Reviews>>
    private lateinit var mMovie: Movie

//        the call to repository method to display the current data according to the user's request
    fun getReviews(movie: Movie): LiveData<List<Reviews>> {
        mMovie = movie
        mReviews = movieRepository.getReviewsFromApi(movie.id)
        return mReviews
    }

    fun setMovie(movie: Movie) {
        movieRepository.saveMovieToDatabase(movie)
    }
}