package com.example.nytimesapplication

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.nytimesapplication.core.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var mActivity:Activity

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)


        registerActivityCallbacks()


    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    /**
     * Workaround to retrieve "activity" instance as using dagger is not working. to be replaced until solution is found
     */
    fun registerActivityCallbacks(){
        registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
                mActivity = activity
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                mActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
                mActivity = activity
            }

        })
    }

}