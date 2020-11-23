package com.example.nytimesapplication.newsDetails.data.repo


import com.example.nytimesapplication.newsDetails.data.remote.NewsDetailsRemoteDataSource


class NewsDetailsRepositoryImpl constructor(
    private val remoteDataSource: NewsDetailsRemoteDataSource
) : NewsDetailsRepository {

}