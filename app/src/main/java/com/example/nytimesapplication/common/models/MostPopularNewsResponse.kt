package com.example.nytimesapplication.common.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

class MostPopularNewsResponse : Serializable {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("results")
    var results: ArrayList<NewsResponse>? = null
}