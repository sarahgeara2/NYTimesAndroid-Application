package com.example.nytimesapplication.mostpopularnews.domain

import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource
import com.example.nytimesapplication.mostpopularnews.data.repo.MostPopularNewsRepository

class MostPopularNewsUseCaseImpl constructor(private val mMostPopularNewsRepository: MostPopularNewsRepository) :
    MostPopularNewsUseCase {
    override fun getMostPopularNews(): MutableLiveData<Resource<MostPopularNewsResponse?>> =
        mMostPopularNewsRepository.getMostPopularNews()

}
