package com.example.nytimesapplication.mostpopularnews.data.repo


import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.core.vo.Resource
import com.example.nytimesapplication.mostpopularnews.data.remote.MostPopularNewsRemoteDataSource
import kotlinx.coroutines.*
import retrofit2.HttpException


class MostPopularNewsRepositoryImpl constructor(
    private val remoteDataSource: MostPopularNewsRemoteDataSource
) : MostPopularNewsRepository {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private var getMostPopularNewsResponse = MutableLiveData<Resource<MostPopularNewsResponse?>>()

    override fun getMostPopularNews(): MutableLiveData<Resource<MostPopularNewsResponse?>> {
        getMostPopularNewsResponse = MutableLiveData()
        getMostPopularNewsResponse.value = Resource.loading(null)
        coroutineScope.launch {
            val request = remoteDataSource.getMostPopularNews()
            withContext(Dispatchers.Main) {
                try {
                    getMostPopularNewsResponse.value = request
                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return getMostPopularNewsResponse
    }

}