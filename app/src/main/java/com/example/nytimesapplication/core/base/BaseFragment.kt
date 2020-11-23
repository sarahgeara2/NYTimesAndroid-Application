package com.example.nytimesapplication.core

import android.app.Activity
import android.view.View
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    lateinit var mActivity: Activity
    var rootView: View? = null

    override fun onResume() {
        super.onResume()
    }




}