package com.example.martinzamarski.moviedatabasekotlin.data.api

import com.example.martinzamarski.moviedatabasekotlin.model.MovieResponse
import com.example.martinzamarski.moviedatabasekotlin.model.ReviewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/3/movie/{sortOrder}?api_key=c797a5f830b5c357c63e52269f7db78c")
    fun getMoviePopular(@Path("sortOrder") sortOrder: String?): Observable<MovieResponse>

    @GET("/3/movie/{id}/reviews?api_key=c797a5f830b5c357c63e52269f7db78c")
    fun getReviews(@Path("id") id: Int?): Observable<ReviewsResponse>
}