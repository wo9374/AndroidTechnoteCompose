package com.example.data.model

import com.google.gson.annotations.SerializedName

data class UnsplashModel(
    @SerializedName("total")
    val total : Int,
    @SerializedName("total_pages")
    val pages : Int,
    @SerializedName("results")
    val results : List<Picture>
)

data class Picture(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val url: Url
)

data class Url(
    @SerializedName("thumb")
    val url: String
)