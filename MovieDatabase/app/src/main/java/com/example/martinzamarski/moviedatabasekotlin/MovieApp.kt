package com.example.martinzamarski.moviedatabasekotlin

import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import com.example.martinzamarski.moviedatabasekotlin.di.AppInjector

import android.app.Application
import android.util.Log
import dagger.android.HasActivityInjector


class MovieApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Log.i("test", " movie app buildConfig")
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}