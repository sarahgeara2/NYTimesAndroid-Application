package com.example.nytimesapplication.newsDetails.data.remote

import com.example.nytimesapplication.core.RestService
import com.example.nytimesapplication.core.util.ResponseHandler


class NewsDetailsRemoteDataSourceImpl constructor(
    private val restService: RestService,
    private val responseHandler: ResponseHandler
) : NewsDetailsRemoteDataSource {


}
