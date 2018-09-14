package com.wanzi.daggerandroid.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import com.wanzi.daggerandroid.LogUtil
import com.wanzi.daggerandroid.R
import com.wanzi.daggerandroid.broadcast.TestReceiver
import com.wanzi.daggerandroid.fragment.TestFragment
import com.wanzi.daggerandroid.service.TestService
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    @Inject
    lateinit var mFragment: TestFragment

    @Inject
    lateinit var mManager: LocalBroadcastManager

    @Inject
    lateinit var mReceiver: TestReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLog.i(value)

        supportFragmentManager.beginTransaction().add(R.id.fl, mFragment).commit()

        startService(Intent(this, TestService::class.java))

        btn.setOnClickListener {
            mManager.sendBroadcast(Intent().setAction("aaa"))
        }
    }

    private fun initReceiver() {
        mManager.registerReceiver(mReceiver, IntentFilter("aaa"))
    }

    override fun onResume() {
        super.onResume()
        initReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        mManager.unregisterReceiver(mReceiver)
    }
}
