package com.example.martinzamarski.moviedatabasekotlin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(@SerializedName("results") @Expose private val results: List<Movie>) : Parcelable {
    fun getResults(): List<Movie> = results
}