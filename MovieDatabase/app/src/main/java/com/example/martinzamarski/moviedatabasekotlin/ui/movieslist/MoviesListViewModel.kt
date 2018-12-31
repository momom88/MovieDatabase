package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist

import androidx.lifecycle.ViewModel
import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.util.SchedulerProvider
import io.reactivex.Observable

import javax.inject.Inject

class MoviesListViewModel @Inject constructor(private val movieRepository: MovieRepository,  private val schedulerProvider: SchedulerProvider) : ViewModel() {

    fun getMovie(sortOrder: String): Observable<List<Movie>> = movieRepository.getMovieList(sortOrder)
        .compose(schedulerProvider.getSchedulersForObservable())
}