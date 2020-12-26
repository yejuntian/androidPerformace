package com.aixuexi.androidperformace

import android.app.Application
import android.content.Context
import androidx.core.os.TraceCompat
import com.aixuexi.androidperformace.net.FrescoTraceListener
import com.aixuexi.androidperformace.utils.LaunchTimer
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.tencent.bugly.crashreport.CrashReport
import java.util.*

open class PerformanceApp : Application() {
    companion object {
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

        //使用systrace代码统计代码时长
        TraceCompat.beginSection("beginSection")

        LaunchTimer.startRecord()
        mApplication = this

        //使用systrace结束统计时长
        TraceCompat.endSection()

        initBugly()
        initFresco()
    }


    private fun initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "2f347fcc43", false);
    }

    private fun initFresco() {
        val listenerSet: MutableSet<RequestListener> = HashSet()
        listenerSet.add(FrescoTraceListener())
        val config = ImagePipelineConfig.newBuilder(mApplication)
            .setRequestListeners(listenerSet)
            .build()
        Fresco.initialize(mApplication, config)
    }

}