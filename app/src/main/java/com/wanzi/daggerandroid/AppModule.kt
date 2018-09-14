package com.wanzi.daggerandroid

import dagger.Module
import dagger.Provides

/**
 *  Author : Created by WZ on 2018-09-14 10:47
 *  E-Mail : 1253427499@qq.com
 */
@Module
class AppModule {

    @Provides
    fun provideLogUtil(): LogUtil = LogUtil()
}