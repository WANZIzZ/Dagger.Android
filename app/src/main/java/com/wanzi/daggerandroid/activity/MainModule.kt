package com.wanzi.daggerandroid.activity

import android.support.v4.content.LocalBroadcastManager
import dagger.Module
import dagger.Provides

/**
 *  Author : Created by WZ on 2018-09-14 11:11
 *  E-Mail : 1253427499@qq.com
 */
@Module
class MainModule {

    @Provides
    fun provideString(): String = "This is Activity"

    @Provides
    fun provideManager(activity: MainActivity): LocalBroadcastManager = LocalBroadcastManager.getInstance(activity)
}