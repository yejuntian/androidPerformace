package com.aixuexi.androidperformace.task

import com.aixuexi.androidperformace.launchstarter.task.Task
import com.tencent.bugly.crashreport.CrashReport

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitBuglyTask : Task() {
    override fun run() {
        CrashReport.initCrashReport(mContext, "2f347fcc43", false)
    }
}