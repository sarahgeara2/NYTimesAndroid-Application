package com.example.nytimesapplication.newsDetails.di


import com.example.nytimesapplication.core.RestService
import com.example.nytimesapplication.core.di.InjectionViewModelProvider
import com.example.nytimesapplication.core.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides
import com.example.nytimesapplication.newsDetails.data.remote.NewsDetailsRemoteDataSource
import com.example.nytimesapplication.newsDetails.data.remote.NewsDetailsRemoteDataSourceImpl
import com.example.nytimesapplication.newsDetails.domain.NewsDetailsUseCase
import com.example.nytimesapplication.newsDetails.domain.NewsDetailsUseCaseImpl
import com.example.nytimesapplication.newsDetails.data.repo.NewsDetailsRepository
import com.example.nytimesapplication.newsDetails.data.repo.NewsDetailsRepositoryImpl
import com.example.nytimesapplication.newsDetails.presentation.ui.fragments.NewsDetailsFragment
import com.example.nytimesapplication.newsDetails.presentation.viewmodel.NewsDetailsViewModel
import com.example.nytimesapplication.core.util.ResponseHandler


@Module
class NewsDetailsModule {

    @Provides
    @ViewModelInjection
    fun provideNewsDetailsViewModel(
        fragment: NewsDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<NewsDetailsViewModel>
    ) = viewModelProvider.get(fragment, NewsDetailsViewModel::class)

    @Provides
    fun providesNewsDetailsUseCase(mNewsDetailsRepository: NewsDetailsRepository): NewsDetailsUseCase =
        NewsDetailsUseCaseImpl(mNewsDetailsRepository)

    @Provides
    fun providesNewsDetailsRepository(mNewsDetailsRemoteDataSource: NewsDetailsRemoteDataSource): NewsDetailsRepository =
        NewsDetailsRepositoryImpl(mNewsDetailsRemoteDataSource)


    @Provides
    fun providesNewsDetailsRemoteDataSource(
        restService: RestService,
        responseHandler: ResponseHandler
    ): NewsDetailsRemoteDataSource =
        NewsDetailsRemoteDataSourceImpl(restService, responseHandler)


}