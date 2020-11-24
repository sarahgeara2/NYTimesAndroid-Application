package com.example.nytimesapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.nytimesapplication.common.models.MostPopularNewsResponse
import com.example.nytimesapplication.common.models.NewsResponse
import com.example.nytimesapplication.core.vo.Resource
import com.example.nytimesapplication.mostpopularnews.domain.MostPopularNewsUseCaseImpl
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MostPopularNewsViewModelTest  {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mostPopularNewsUseCaseImpl : MostPopularNewsUseCaseImpl

    private lateinit var testSingle: MutableLiveData<Resource<MostPopularNewsResponse?>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setupJavaRx() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }

    }

    @Test
    fun getMostPopularNewsSuccess() {
        testSingle = MutableLiveData<Resource<MostPopularNewsResponse?>>()
        var mostPopularNewsResponse = MostPopularNewsResponse()
        mostPopularNewsResponse.status="OK"
        var newsResponse = NewsResponse()
        newsResponse.id="1"
        newsResponse.title="title"
        var newsResponse1 = NewsResponse()
        newsResponse1.id="2"
        newsResponse1.title="title"
        mostPopularNewsResponse.results = arrayListOf(newsResponse,newsResponse1)
        testSingle.value = Resource.success(mostPopularNewsResponse)
        Mockito.`when`(mostPopularNewsUseCaseImpl.getMostPopularNews()).thenReturn(testSingle)
        mostPopularNewsUseCaseImpl.getMostPopularNews()
        Assert.assertEquals(2, mostPopularNewsUseCaseImpl.getMostPopularNews().value!!.data!!.results!!.size)
    }


    @Test
    fun getMostPopularNewsFailure() {
        testSingle = MutableLiveData<Resource<MostPopularNewsResponse?>>()
        var mostPopularNewsResponse = MostPopularNewsResponse()
        testSingle.value = Resource.success(mostPopularNewsResponse)
        Mockito.`when`(mostPopularNewsUseCaseImpl.getMostPopularNews()).thenReturn(testSingle)
        mostPopularNewsUseCaseImpl.getMostPopularNews()
        Assert.assertEquals(null, mostPopularNewsUseCaseImpl.getMostPopularNews().value)
    }


}