package com.example.nytimesapplication.core

import com.example.nytimesapplication.common.GlobalVars
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.util.NoInternetConnection
import com.example.nytimesapplication.core.util.Retry
import retrofit2.http.*

interface RestService {

    @Retry
    @NoInternetConnection
    @GET(GlobalVars.GET_MOST_POPULAR_NEWS)
    suspend fun getMostPopularNews(
        @Query("api-key") apiKey:String
    ): MostPopularNewsResponse?

}