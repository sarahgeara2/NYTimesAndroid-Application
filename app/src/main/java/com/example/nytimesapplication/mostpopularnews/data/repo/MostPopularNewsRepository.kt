package com.example.nytimesapplication.mostpopularnews.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource

interface MostPopularNewsRepository {
    fun getMostPopularNews(): MutableLiveData<Resource<MostPopularNewsResponse?>>
}