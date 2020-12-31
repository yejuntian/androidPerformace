package com.aixuexi.androidperformace.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/30/20]
 */
class DeviceIdUtil {
    fun getDeviceId(context: Context): String? {
        val sbDeviceId = StringBuilder()
        val imei = getIMEI(context)
        val androidid = getAndroidId(context)
        val serial = getSERIAL()
        val uuid = getDeviceUUID().replace("-", "")
        if (imei != null && imei.length > 0) {
            sbDeviceId.append(imei)
            sbDeviceId.append("|")
        }
        if (androidid != null && androidid.length > 0) {
            sbDeviceId.append(androidid)
            sbDeviceId.append("|")
        }
        if (serial != null && serial.length > 0) {
            sbDeviceId.append(serial)
            sbDeviceId.append("|")
        }
        if (uuid != null && uuid.length > 0) {
            sbDeviceId.append(uuid)
        }
        if (sbDeviceId.length > 0) {
            try {
                val hash = getHashByString(sbDeviceId.toString())
                val sha1 = bytesToHex(hash)
                if (sha1 != null && sha1.length > 0) {
                    return sha1
                }
            } catch (var8: Exception) {
                var8.printStackTrace()
            }
        }
        return UUID.randomUUID().toString().replace("-", "")
    }

    @SuppressLint("WrongConstant")
    private fun getIMEI(context: Context): String? {
        return try {
            val tm = context.getSystemService("phone") as TelephonyManager
            tm.deviceId
        } catch (var2: Exception) {
            var2.printStackTrace()
            ""
        }
    }

    private fun getAndroidId(context: Context): String? {
        return try {
            Secure.getString(context.contentResolver, "android_id")
        } catch (var2: Exception) {
            var2.printStackTrace()
            ""
        }
    }

    private fun getSERIAL(): String? {
        return try {
            Build.SERIAL
        } catch (var1: Exception) {
            var1.printStackTrace()
            ""
        }
    }

    private fun getDeviceUUID(): String {
        return try {
            val dev =
                "930828" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.HARDWARE.length % 10 + Build.ID.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.SERIAL.length % 10
            UUID(
                dev.hashCode().toLong(),
                Build.SERIAL.hashCode().toLong()
            ).toString()
        } catch (var1: Exception) {
            var1.printStackTrace()
            ""
        }
    }

    private fun getHashByString(data: String): ByteArray {
        return try {
            val messageDigest =
                MessageDigest.getInstance("SHA1")
            messageDigest.reset()
            messageDigest.update(data.toByteArray(charset("UTF-8")))
            messageDigest.digest()
        } catch (var2: Exception) {
            "".toByteArray()
        }
    }

    private fun bytesToHex(data: ByteArray): String? {
        val sb = StringBuilder()
        for (n in data.indices) {
            val stmp = Integer.toHexString((data[n] and 255.toByte()).toInt())
            if (stmp.length == 1) {
                sb.append("0")
            }
            sb.append(stmp)
        }
        return sb.toString().toUpperCase(Locale.CHINA)
    }
}