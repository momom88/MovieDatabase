package com.example.martinzamarski.moviedatabasekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie (

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float,

    @SerializedName("title")
    @Expose
    val
    title: String,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String,

    @SerializedName("overview")
    @Expose
    val overview: String,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String

) : Parcelable
