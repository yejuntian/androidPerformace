package com.aixuexi.androidperformace.task

import com.aixuexi.androidperformace.PerformanceApp
import com.aixuexi.androidperformace.launchstarter.task.Task
import com.aixuexi.androidperformace.utils.DeviceIdUtil

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class GetDeviceIdTask : Task() {
    override fun run() {
        val application = mContext as PerformanceApp
        application.setDeviceId(DeviceIdUtil().getDeviceId(mContext))
    }
}