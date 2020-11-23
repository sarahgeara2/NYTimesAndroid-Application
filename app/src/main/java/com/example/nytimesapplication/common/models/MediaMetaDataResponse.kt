package com.example.nytimesapplication.common.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MediaMetaDataResponse : Serializable {
    @SerializedName("url")
    var url: String? = null
    @SerializedName("format")
    var format: String? = null
    @SerializedName("height")
    var height: Int? = null
    @SerializedName("width")
    var width: Int? = null
}