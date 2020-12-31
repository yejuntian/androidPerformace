package com.aixuexi.androidperformace.task

import android.app.Application
import com.aixuexi.androidperformace.launchstarter.task.MainTask
import com.aixuexi.androidperformace.launchstarter.task.Task
import org.apache.weex.InitConfig
import org.apache.weex.WXSDKEngine

/**
 * 主线程执行的task
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitWeexTask : MainTask() {

    //在被调用await()的地方执行完成，即onCreate()执行完成，保证其他页面调用weex方法的时候，已经执行完成
    override fun needWait(): Boolean {
        return true
    }

    override fun run() {
        val config = InitConfig.Builder().build()
        WXSDKEngine.initialize(mContext as Application?, config)
    }
}