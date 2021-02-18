package com.aixuexi.androidperformace.net

import android.content.Context
import com.alibaba.sdk.android.httpdns.HttpDns
import com.alibaba.sdk.android.httpdns.HttpDnsService
import okhttp3.Dns
import java.net.InetAddress
import java.util.*

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2/18/21]
 */
class OkHttpDns(context: Context?) : Dns {
    //阿里云提供的HttpDns解析服务
    private var dnsService: HttpDnsService? = null

    init {
        //用户id这里演示直接写的""
        dnsService = HttpDns.getService(context, "")
    }
    companion object{
        private var instance: OkHttpDns? = null
        fun getInstance(context: Context?): OkHttpDns {
            if (instance == null) {
                synchronized(OkHttpDns::class.java) {
                    if (instance == null) {
                        instance = OkHttpDns(context)
                    }
                }
            }
            return instance!!
        }
    }

    override fun lookup(hostname: String): MutableList<InetAddress> {
        //优先使用阿里云dns解析服务返回的ip地址，如果为空再走系统的DNS解析服务
        val ip = dnsService?.getIpByHostAsync(hostname)
        //如果不为空直接使用这个ip进行网络请求
        if (ip != null) {
            return Arrays.asList(*InetAddress.getAllByName(ip))
        }
        //如果为空走系统的解析服务
        return Dns.SYSTEM.lookup(hostname)
    }
}