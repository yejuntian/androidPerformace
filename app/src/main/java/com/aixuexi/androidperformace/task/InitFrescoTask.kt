package com.aixuexi.androidperformace.task

import com.aixuexi.androidperformace.launchstarter.task.Task
import com.aixuexi.androidperformace.net.FrescoTraceListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import java.util.*

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class InitFrescoTask : Task() {
    override fun run() {
        val listenerSet: MutableSet<RequestListener> = HashSet()
        listenerSet.add(FrescoTraceListener())
        val config = ImagePipelineConfig.newBuilder(mContext)
            .setRequestListeners(listenerSet)
            .build()
        Fresco.initialize(mContext, config)
    }
}