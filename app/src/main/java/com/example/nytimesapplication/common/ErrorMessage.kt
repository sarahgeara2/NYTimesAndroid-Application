package com.example.nytimesapplication.common


import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)