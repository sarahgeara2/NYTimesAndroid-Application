package com.example.nytimesapplication.common.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NewsResponse : Serializable {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("published_date")
    var publishedDate: String? = null
    @SerializedName("section")
    var section: String? = null
    @SerializedName("byline")
    var byline: String? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("abstract")
    var abstract: String? = null
    @SerializedName("media")
    var media: ArrayList<MediaResponse>? = null
}