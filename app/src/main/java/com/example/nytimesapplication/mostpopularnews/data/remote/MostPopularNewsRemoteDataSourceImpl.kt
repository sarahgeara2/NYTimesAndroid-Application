package com.example.nytimesapplication.mostpopularnews.data.remote

import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.RestService
import com.example.nytimesapplication.core.vo.Resource
import com.example.nytimesapplication.core.util.ResponseHandler


class MostPopularNewsRemoteDataSourceImpl constructor(
    private val restService: RestService,
    private val responseHandler: ResponseHandler
) : MostPopularNewsRemoteDataSource {
    override suspend fun getMostPopularNews(): Resource<MostPopularNewsResponse?> {
        return try {
            responseHandler.handleSuccess(restService.getMostPopularNews("GHGtpO5UDi2aKF9yKYwVpoMFgaKK5q8j"))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}
