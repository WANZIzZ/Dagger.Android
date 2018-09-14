package com.wanzi.daggerandroid.broadcast

import android.content.Context
import android.content.Intent
import com.wanzi.daggerandroid.LogUtil
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class TestReceiver @Inject constructor() : DaggerBroadcastReceiver() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        mLog.i(value)
    }
}
