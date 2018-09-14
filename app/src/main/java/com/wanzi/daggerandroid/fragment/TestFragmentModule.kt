package com.wanzi.daggerandroid.fragment

import dagger.Module
import dagger.Provides

/**
 *  Author : Created by WZ on 2018-09-14 11:46
 *  E-Mail : 1253427499@qq.com
 */
@Module
class TestFragmentModule {

    @Provides
    fun provideString(): String = "This is Fragment"
}