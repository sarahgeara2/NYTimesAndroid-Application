package com.example.nytimesapplication.mostpopularnews.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource
import javax.inject.Inject
import com.example.nytimesapplication.mostpopularnews.domain.MostPopularNewsUseCase
import com.example.nytimesapplication.core.BaseViewModel


class MostPopularNewsViewModel @Inject constructor(
    private val mMostPopularNewsUseCase: MostPopularNewsUseCase
) : BaseViewModel() {

    var getMostPopularNewsViewModel: MutableLiveData<Resource<MostPopularNewsResponse?>> = MutableLiveData()
    fun getMostPopularNews() {
        getMostPopularNewsViewModel = mMostPopularNewsUseCase.getMostPopularNews()
    }
}