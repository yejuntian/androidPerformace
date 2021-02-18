package com.aixuexi.androidperformace

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import cn.jpush.android.api.JPushInterface
import com.aixuexi.androidperformace.launchstarter.TaskDispatcher
import com.aixuexi.androidperformace.memory.ImageHook
import com.aixuexi.androidperformace.net.FrescoTraceListener
import com.aixuexi.androidperformace.task.*
import com.aixuexi.androidperformace.utils.DeviceIdUtil
import com.aixuexi.androidperformace.utils.LaunchTimer
import com.aixuexi.androidperformace.utils.LogUtils
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.stetho.Stetho
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook
import org.apache.weex.InitConfig
import org.apache.weex.WXSDKEngine
import java.util.*

open class PerformanceApp : Application() {
    var mDeviceId: String? = ""

    companion object {
        private var mApplication: Application? = null

        fun getApplication(): Application {
            return mApplication!!
        }
    }

    //开始统计耗时的方法
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        LaunchTimer.startRecord()
        //采用hook的方式查找所有线程调用地方是否合理
//        DexposedBridge.hookAllConstructors(Thread::class.java, object : XC_MethodHook() {
//            @Throws(Throwable::class)
//            override fun afterHookedMethod(param: MethodHookParam) {
//                super.afterHookedMethod(param)
//                val thread = param.thisObject as Thread
//                LogUtils.i(thread.name + " stack " + Log.getStackTraceString(Throwable()))
//            }
//        })
    }


    override fun onCreate() {
        super.onCreate()
        LaunchTimer.startRecord()
        mApplication = this

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

        DexposedBridge.hookAllConstructors(ImageView::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
                DexposedBridge.findAndHookMethod(
                    ImageView::class.java,
                    "setImageBitmap",
                    Bitmap::class.java,
                    ImageHook()
                )
            }
        })
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