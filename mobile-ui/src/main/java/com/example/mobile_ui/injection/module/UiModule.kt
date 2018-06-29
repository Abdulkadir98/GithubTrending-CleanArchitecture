package com.example.mobile_ui.injection.module

import com.example.mobile_ui.UiThread
import com.example.domain.executor.PostExecutionThread
import com.example.mobile_ui.bookmarked.BookmarkedActivity
import com.example.mobile_ui.browse.BrowseActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity
}