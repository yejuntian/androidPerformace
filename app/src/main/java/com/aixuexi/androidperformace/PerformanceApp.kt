package com.aixuexi.androidperformace

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.core.os.TraceCompat
import cn.jpush.android.api.JPushInterface
import com.aixuexi.androidperformace.launchstarter.TaskDispatcher
import com.aixuexi.androidperformace.net.FrescoTraceListener
import com.aixuexi.androidperformace.task.*
import com.aixuexi.androidperformace.utils.DeviceIdUtil
import com.aixuexi.androidperformace.utils.LaunchTimer
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.stetho.Stetho
import org.apache.weex.InitConfig
import org.apache.weex.WXSDKEngine
import java.util.HashSet

open class PerformanceApp : Application() {
    var mDeviceId: String? = ""

    companion object {
        private var mApplication: Application? = null

        fun getApplication(): Application? {
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


        TaskDispatcher.init(this)
        val instance = TaskDispatcher.createInstance()
        instance.addTask(InitAMapTask())
            .addTask(InitStethoTask())
            .addTask(InitWeexTask())
            .addTask(InitBuglyTask())
            .addTask(InitFrescoTask())
            .addTask(InitJPushTask())
            .addTask(GetDeviceIdTask())
            .start()
        instance.await()

    }


    open fun setDeviceId(deviceId: String?) {
        mDeviceId = deviceId
    }

    open fun getDeviceId(): String? {
        return mDeviceId
    }



    private fun initFresco() {
        val listenerSet: MutableSet<RequestListener> = HashSet()
        listenerSet.add(FrescoTraceListener())
        val config = ImagePipelineConfig.newBuilder(mApplication)
            .setRequestListeners(listenerSet)
            .build()
        Fresco.initialize(mApplication, config)
    }

    private fun initAMap() {
        val mLocationClient = AMapLocationClient(mApplication)
        val mLocationListener = AMapLocationListener {
            // 一些处理
        }
        mLocationClient.setLocationListener(mLocationListener)
        val mLocationOption = AMapLocationClientOption()
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving)
        mLocationOption.setOnceLocation(true)
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.startLocation()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private fun initWeex() {
        val config = InitConfig.Builder().build()
        WXSDKEngine.initialize(mApplication, config)
    }

    private fun initJPush() {
        JPushInterface.setAlias(mApplication, 0, mDeviceId)
    }

    private fun getDeviceId2() {
        mDeviceId = mApplication?.let { DeviceIdUtil().getDeviceId(it) }
    }

}