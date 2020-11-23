package com.example.nytimesapplication.core.di.modules

import com.example.nytimesapplication.mostpopularnews.di.MostPopularNewsModule
import com.example.nytimesapplication.mostpopularnews.presentation.ui.fragments.MostPopularNewsFragment
import com.example.nytimesapplication.newsDetails.di.NewsDetailsModule
import com.example.nytimesapplication.newsDetails.presentation.ui.fragments.NewsDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [MostPopularNewsModule::class])
    abstract fun mostPopularNewsFragmentInjector(): MostPopularNewsFragment

    @ContributesAndroidInjector(modules = [NewsDetailsModule::class])
    abstract fun newsDetailsFragmentInjector(): NewsDetailsFragment

}