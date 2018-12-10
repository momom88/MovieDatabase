package com.example.martinzamarski.moviedatabasekotlin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reviews(

    @SerializedName("author")
    @Expose
    val author: String,

    @SerializedName("content")
    @Expose
    val content: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("url")
    @Expose
    val url: String

) : Parcelable