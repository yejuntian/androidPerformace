package com.aixuexi.androidperformace.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.JPushInterface

/**
 * 自定义接收器
 *
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            when {
                JPushInterface.ACTION_REGISTRATION_ID == intent.action -> {
                    val regId = bundle!!.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                    Log.d(TAG, "[MyReceiver] 接收Registration Id : $regId")
                    //send the Registration Id to your server...
                }
                JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> {
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle!!.getString(JPushInterface.EXTRA_MESSAGE))
                }
                JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> {
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知")
                    val notifactionId = bundle!!.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId"
                    )
                }
                JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                    Log.d(TAG, "[MyReceiver] 用户点击打开了通知")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "JIGUANG-Example"
    }
}