package com.aixuexi.androidperformace.memory

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import com.aixuexi.androidperformace.utils.LogUtils
import com.taobao.android.dexposed.XC_MethodHook

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:1/5/21]
 */
class ImageHook : XC_MethodHook() {
    override fun afterHookedMethod(param: MethodHookParam?) {
        super.afterHookedMethod(param)
        //实现我们的逻辑
        val ImageView = param?.thisObject

    }

    private fun checkBitmap(thiz: Any, drawable: Drawable) {
        if (drawable is BitmapDrawable && thiz is View) {
            val bitmap = drawable.bitmap
            if (bitmap != null) {
                val view = thiz
                val width = view.width
                val height = view.height
                if (width > 0 && height > 0) {
                    // 图标宽高都大于view带下的2倍以上，则警告
                    if (bitmap.width >= width shl 1 && bitmap.height >= height shl 1) {
                        warn(
                            bitmap.width,
                            bitmap.height,
                            width,
                            height,
                            RuntimeException("Bitmap size too large")
                        )
                    }
                } else {
                    val stackTrace: Throwable = RuntimeException()
                    view.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            val w = view.width
                            val h = view.height
                            if (w > 0 && h > 0) {
                                if (bitmap.width >= w shl 1 && bitmap.height >= h shl 1) {
                                    warn(bitmap.width, bitmap.height, w, h, stackTrace)
                                }
                                view.viewTreeObserver.removeOnPreDrawListener(this)
                            }
                            return true
                        }
                    })
                }
            }
        }
    }


    private fun warn(bitmapWidth: Int, bitmapHeight: Int, viewWidth: Int, viewHeight: Int, t: Throwable) {
        val warnInfo = StringBuilder("Bitmap size too large: ")
            .append("\n real size: (").append(bitmapWidth).append(',').append(bitmapHeight)
            .append(')')
            .append("\n desired size: (").append(viewWidth).append(',').append(viewHeight)
            .append(')')
            .append("\n call stack trace: \n").append(Log.getStackTraceString(t))
            .append('\n')
            .toString()
        LogUtils.i(warnInfo)
    }

}