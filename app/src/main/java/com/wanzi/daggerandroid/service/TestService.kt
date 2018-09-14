package com.wanzi.daggerandroid.service

import android.content.Intent
import android.os.IBinder
import com.wanzi.daggerandroid.LogUtil
import dagger.android.DaggerService
import javax.inject.Inject

class TestService : DaggerService() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        mLog.i(value)
    }
}
