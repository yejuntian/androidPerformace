package com.aixuexi.androidperformace.task

import cn.jpush.android.api.JPushInterface
import com.aixuexi.androidperformace.PerformanceApp
import com.aixuexi.androidperformace.launchstarter.task.Task
import com.aixuexi.androidperformace.utils.LogUtils

/**
 * 需要在getDeviceId（）之后执行
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitJPushTask : Task() {
    override fun dependsOn(): MutableList<Class<out Task>> {
        val task: MutableList<Class<out Task?>> = mutableListOf()
        task.add(GetDeviceIdTask::class.java)
        return task
    }

    override fun run() {
        val application = mContext as PerformanceApp
        LogUtils.e("deviceId = ${application.getDeviceId()}")
        JPushInterface.setAlias(mContext, 0, application.getDeviceId())
    }
}