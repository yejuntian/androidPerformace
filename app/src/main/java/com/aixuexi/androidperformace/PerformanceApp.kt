package com.aixuexi.androidperformace

import android.app.Application
import android.content.Context
import com.aixuexi.androidperformace.utils.LaunchTimer

open class PerformanceApp : Application() {
    companion object{
        private var mApplication: Application? = null

        open fun getApplication(): Application? {
            return mApplication
        }
    }

    //开始统计耗时的方法
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        LaunchTimer.startRecord()
    }


    override fun onCreate() {
        super.onCreate()
        LaunchTimer.startRecord()
        mApplication = this
    }
}