package com.wanzi.daggerandroid

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 *  Author : Created by WZ on 2018-09-14 10:46
 *  E-Mail : 1253427499@qq.com
 */
class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}