package com.example.martinzamarski.moviedatabasekotlin.data.api

import com.example.martinzamarski.moviedatabasekotlin.model.MovieResponse
import com.example.martinzamarski.moviedatabasekotlin.model.ReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/3/movie/popular?api_key=c797a5f830b5c357c63e52269f7db78c")
    fun getMoviePopular():Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=c797a5f830b5c357c63e52269f7db78c")
    fun getMovieTopRated(): Call<MovieResponse>

    @GET("/3/movie/{id}/reviews?api_key=c797a5f830b5c357c63e52269f7db78c")
    fun getReviews(@Path("id") id: Int?): Call<ReviewsResponse>
}