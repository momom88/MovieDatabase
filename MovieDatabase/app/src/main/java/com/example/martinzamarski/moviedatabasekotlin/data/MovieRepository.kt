package com.example.martinzamarski.moviedatabasekotlin.data

import com.example.martinzamarski.moviedatabasekotlin.data.api.ApiInterface
import com.example.martinzamarski.moviedatabasekotlin.data.database.MovieDao
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.MovieResponse
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_FAVORITE

import io.reactivex.Observable

import javax.inject.Inject
import com.example.martinzamarski.moviedatabasekotlin.model.ReviewsResponse
import io.reactivex.Single
import kotlin.collections.HashMap


class MovieRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val movieDao: MovieDao
) {
    private var cachedListMovie = HashMap<String, List<Movie>>()
    private var cachedReviews = HashMap<Int, List<Reviews>>()

    fun getMovieList(sortOrder: String): Observable<List<Movie>> {
        return if (sortOrder == MOVIE_FAVORITE) {
            getFavoriteMovies()
        } else getMovies(sortOrder)
    }

    fun getReviews(id: Int): Observable<List<Reviews>> {
        return if ((cachedReviews[id].isNullOrEmpty())) {
            apiInterface.getReviews(id)
                .map(ReviewsResponse::getResults)
                .doOnNext {
                    cachedReviews[id] = it
                }
        } else {
            Observable.just(cachedReviews[id])
        }
    }

    private fun getMovies(sortOrder: String): Observable<List<Movie>> {
        return if (!cachedListMovie.containsKey(sortOrder)) {
            apiInterface.getMoviePopular(sortOrder)
                .map(MovieResponse::getResults)
                .doOnNext {
                    cachedListMovie[sortOrder] = it
                }
        } else {
            Observable.just(cachedListMovie[sortOrder])
        }
    }

    private fun getFavoriteMovies(): Observable<List<Movie>> {
        return movieDao.loadAllMovie().toObservable()
    }

    fun saveMovie(movie: Movie): Single<Unit> {
        return Single.fromCallable {
            movieDao.insertMovie(movie)
        }
    }
}



