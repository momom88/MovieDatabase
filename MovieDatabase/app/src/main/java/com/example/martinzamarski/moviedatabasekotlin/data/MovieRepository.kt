package com.example.martinzamarski.moviedatabasekotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_POPULAR
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_TOP_RATED
import com.example.martinzamarski.moviedatabasekotlin.data.api.ApiInterface
import com.example.martinzamarski.moviedatabasekotlin.data.database.MovieDao
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.MovieResponse
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.model.ReviewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val movieDao: MovieDao){

    //the method download the current data from api or load from database
    fun getMovieList(sortOrder: Int): LiveData<List<Movie>> {
        return if(sortOrder == MOVIE_TOP_RATED || sortOrder == MOVIE_POPULAR) {
            getMovieFromApi(sortOrder)
        } else movieDao.loadAllMovie()
    }

    private fun getMovieFromApi(sortOrder: Int): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        var call: Call<MovieResponse>? = null
        if (sortOrder == MOVIE_POPULAR) {
            call = apiInterface.getMoviePopular()
        } else if (sortOrder == MOVIE_TOP_RATED) {
            call = apiInterface.getMovieTopRated()
        }
        call?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val body = response.body()!!.getResults()
                if (response.isSuccessful) {
                    Log.i("test", "get movie from api: $body")
                    data.postValue(body)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i("test", "Retrofit test onFailure: $t")
            }

        })
        return data
    }

    fun getReviewsFromApi(id: Int): LiveData<List<Reviews>> {
        val call = apiInterface.getReviews(id)
        val data = MutableLiveData<List<Reviews>>()
        call.enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                val body = response.body()!!.getResults()
                if (response.isSuccessful) {
                    Log.d("test", "onresponse ok")
                    data.setValue(body)
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                Log.d("test", "getReviewsFromApi retrofit onFailure$t")
            }
        })
        return data
    }

    fun saveMovieToDatabase(movie: Movie) {
        movieDao.insertMovie(movie)
    }
}


