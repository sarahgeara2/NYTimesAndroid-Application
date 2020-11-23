package com.example.nytimesapplication.newsDetails.presentation.viewmodel


import javax.inject.Inject
import com.example.nytimesapplication.newsDetails.domain.NewsDetailsUseCase
import com.example.nytimesapplication.core.BaseViewModel


class NewsDetailsViewModel @Inject constructor(
    private val mNewsDetailsUseCase: NewsDetailsUseCase
) : BaseViewModel() {


}