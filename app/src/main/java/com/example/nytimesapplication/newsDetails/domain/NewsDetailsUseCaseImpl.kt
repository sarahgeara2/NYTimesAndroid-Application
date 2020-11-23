package com.example.nytimesapplication.newsDetails.domain

import com.example.nytimesapplication.newsDetails.data.repo.NewsDetailsRepository

class NewsDetailsUseCaseImpl constructor(private val mNewsDetailsRepository: NewsDetailsRepository) :
    NewsDetailsUseCase {

}
