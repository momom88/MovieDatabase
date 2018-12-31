package com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity

import androidx.lifecycle.ViewModel
import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject


class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    fun getReviews(id: Int): Observable<List<Reviews>> = movieRepository.getReviews(id)
        .compose(schedulerProvider.getSchedulersForObservable())

    @Suppress("HasPlatformType")
    fun saveToFavorite(movie: Movie) = movieRepository.saveMovie(movie)
                .compose(schedulerProvider.getSchedulersForSingle())
    }