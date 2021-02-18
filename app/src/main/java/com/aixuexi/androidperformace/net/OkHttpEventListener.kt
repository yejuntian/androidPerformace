package com.aixuexi.androidperformace.net

import android.util.Log
import okhttp3.*
import okhttp3.EventListener.Factory
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

class OkHttpEventListener : EventListener() {
    private var okHttpEvent:OkHttpEvent?=null
    init {
        okHttpEvent = OkHttpEvent()
    }

    companion object{
        val factory = Factory { OkHttpEventListener() }
    }

    override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy?, protocol: Protocol?, ioe: IOException?) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy?, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        okHttpEvent?.dnsStartTime = System.currentTimeMillis()
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: MutableList<InetAddress>?) {
        super.dnsEnd(call, domainName, inetAddressList)
        okHttpEvent?.dnsEndTime = System.currentTimeMillis()
    }

    override fun callStart(call: Call) {
        super.callStart(call)
        Log.i("Test", "callStart")
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        Log.i("Test", "callEnd")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        okHttpEvent?.apiSuccess = true
        okHttpEvent?.responseBodySize = byteCount
    }

    override fun callFailed(call: Call, ioe: IOException) {
        Log.i("Test", "callFailed ")
        super.callFailed(call, ioe)
        okHttpEvent?.apiSuccess = false
        okHttpEvent?.errorReason = Log.getStackTraceString(ioe)
        Log.i("Test", "reason " + okHttpEvent?.errorReason)
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
    }


    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
    }
}