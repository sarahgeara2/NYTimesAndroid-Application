package com.example.nytimesapplication.common.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MediaResponse : Serializable {
    @SerializedName("type")
    var type: String? = null
    @SerializedName("subtype")
    var subtype: String? = null
    @SerializedName("caption")
    var caption: String? = null
    @SerializedName("media-metadata")
    var medias: ArrayList<MediaMetaDataResponse>? = null

}