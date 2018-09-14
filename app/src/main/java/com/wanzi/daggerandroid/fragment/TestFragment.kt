package com.wanzi.daggerandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanzi.daggerandroid.LogUtil
import com.wanzi.daggerandroid.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 *  Author : Created by WZ on 2018-09-14 11:43
 *  E-Mail : 1253427499@qq.com
 */
class TestFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLog.i(value)
    }
}