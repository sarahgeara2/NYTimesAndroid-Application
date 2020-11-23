package com.example.nytimesapplication.core.di.modules

import com.example.nytimesapplication.mostpopularnews.presentation.ui.activities.MainActivity
import com.example.nytimesapplication.newsDetails.presentation.ui.activities.NewsDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun newsDetailsActivityInjector(): NewsDetailsActivity

}