package com.wanzi.daggerandroid.broadcast

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *  Author : Created by WZ on 2018-09-14 11:10
 *  E-Mail : 1253427499@qq.com
 */
@Module
abstract class BroadcastBindingModule {

    @ContributesAndroidInjector(modules = [TestReceiverModule::class])
    abstract fun bindTestReceiver(): TestReceiver
}