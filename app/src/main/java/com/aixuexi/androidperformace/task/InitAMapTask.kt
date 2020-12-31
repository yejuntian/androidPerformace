package com.aixuexi.androidperformace.task

import com.aixuexi.androidperformace.launchstarter.task.Task
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitAMapTask : Task() {
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    val mLocationListener = AMapLocationListener {
        // 一些处理
    }

    override fun run() {
        mLocationClient = AMapLocationClient(mContext)
        mLocationClient?.setLocationListener(mLocationListener)
        mLocationOption = AMapLocationClientOption()
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption?.isOnceLocation = true
        mLocationClient?.setLocationOption(mLocationOption)
        mLocationClient?.startLocation()
    }
}