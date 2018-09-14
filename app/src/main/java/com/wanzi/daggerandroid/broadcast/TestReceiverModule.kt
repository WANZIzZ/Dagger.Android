package com.wanzi.daggerandroid.broadcast

import dagger.Module
import dagger.Provides

/**
 *  Author : Created by WZ on 2018-09-14 14:23
 *  E-Mail : 1253427499@qq.com
 */
@Module
class TestReceiverModule {

    @Provides
    fun provideString(): String = "This is Broadcast"
}