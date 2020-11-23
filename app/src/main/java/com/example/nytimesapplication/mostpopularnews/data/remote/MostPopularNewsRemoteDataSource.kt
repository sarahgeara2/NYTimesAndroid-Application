package com.example.nytimesapplication.mostpopularnews.data.remote

import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource


interface MostPopularNewsRemoteDataSource {

    suspend fun getMostPopularNews(): Resource<MostPopularNewsResponse?>

}