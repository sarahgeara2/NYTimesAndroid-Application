package com.example.nytimesapplication.mostpopularnews.domain

import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource


interface MostPopularNewsUseCase {
    fun getMostPopularNews(): MutableLiveData<Resource<MostPopularNewsResponse?>>

}