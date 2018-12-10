package com.example.martinzamarski.moviedatabasekotlin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewsResponse(@SerializedName("results") @Expose private val results: List<Reviews>
) : Parcelable {
    fun getResults(): List<Reviews> = results
}