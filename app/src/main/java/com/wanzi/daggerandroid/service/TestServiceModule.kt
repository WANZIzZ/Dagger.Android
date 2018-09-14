package com.wanzi.daggerandroid.service

import dagger.Module
import dagger.Provides

/**
 *  Author : Created by WZ on 2018-09-14 14:01
 *  E-Mail : 1253427499@qq.com
 */
@Module
class TestServiceModule {

    @Provides
    fun provideString(): String = "This is Service"
}