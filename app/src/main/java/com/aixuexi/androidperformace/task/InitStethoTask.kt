package com.aixuexi.androidperformace.task

import com.aixuexi.androidperformace.launchstarter.task.Task
import com.facebook.stetho.Stetho

/**
 * 异步的Task
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitStethoTask : Task() {
    override fun run() {
        Stetho.initializeWithDefaults(mContext)
    }
}