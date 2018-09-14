package com.wanzi.daggerandroid

import android.app.Application
import com.wanzi.daggerandroid.activity.ActivityBindingModule
import com.wanzi.daggerandroid.broadcast.BroadcastBindingModule
import com.wanzi.daggerandroid.fragment.FragmentBindingModule
import com.wanzi.daggerandroid.service.ServiceBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 *  Author : Created by WZ on 2018-09-14 10:48
 *  E-Mail : 1253427499@qq.com
 */
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    FragmentBindingModule::class,
    ServiceBindingModule::class,
    BroadcastBindingModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}