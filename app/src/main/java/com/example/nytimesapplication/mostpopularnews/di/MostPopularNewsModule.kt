package com.example.nytimesapplication.mostpopularnews.di


import com.example.nytimesapplication.core.RestService
import com.example.nytimesapplication.core.di.InjectionViewModelProvider
import com.example.nytimesapplication.core.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides
import com.example.nytimesapplication.mostpopularnews.data.remote.MostPopularNewsRemoteDataSource
import com.example.nytimesapplication.mostpopularnews.data.remote.MostPopularNewsRemoteDataSourceImpl
import com.example.nytimesapplication.mostpopularnews.domain.MostPopularNewsUseCase
import com.example.nytimesapplication.mostpopularnews.domain.MostPopularNewsUseCaseImpl
import com.example.nytimesapplication.mostpopularnews.data.repo.MostPopularNewsRepository
import com.example.nytimesapplication.mostpopularnews.data.repo.MostPopularNewsRepositoryImpl
import com.example.nytimesapplication.mostpopularnews.presentation.ui.fragments.MostPopularNewsFragment
import com.example.nytimesapplication.mostpopularnews.presentation.viewmodel.MostPopularNewsViewModel
import com.example.nytimesapplication.core.util.ResponseHandler


@Module
class MostPopularNewsModule {

    @Provides
    @ViewModelInjection
    fun provideMostPopularNewsViewModel(
        fragment: MostPopularNewsFragment,
        viewModelProvider: InjectionViewModelProvider<MostPopularNewsViewModel>
    ) = viewModelProvider.get(fragment, MostPopularNewsViewModel::class)

    @Provides
    fun providesMostPopularNewsUseCase(mMostPopularNewsRepository: MostPopularNewsRepository): MostPopularNewsUseCase =
        MostPopularNewsUseCaseImpl(mMostPopularNewsRepository)

    @Provides
    fun providesMostPopularNewsRepository(mMostPopularNewsRemoteDataSource: MostPopularNewsRemoteDataSource): MostPopularNewsRepository =
        MostPopularNewsRepositoryImpl(mMostPopularNewsRemoteDataSource)


    @Provides
    fun providesMostPopularNewsRemoteDataSource(
        restService: RestService,
        responseHandler: ResponseHandler
    ): MostPopularNewsRemoteDataSource =
        MostPopularNewsRemoteDataSourceImpl(restService, responseHandler)


}