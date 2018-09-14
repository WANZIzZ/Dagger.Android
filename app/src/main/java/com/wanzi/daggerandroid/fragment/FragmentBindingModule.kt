package com.wanzi.daggerandroid.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *  Author : Created by WZ on 2018-09-14 11:10
 *  E-Mail : 1253427499@qq.com
 */
@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [TestFragmentModule::class])
    abstract fun bindTestFragment(): TestFragment
}