package com.example.mobile_ui

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class GithubTrendingApplication: Application(), HasActivityInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()




    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}