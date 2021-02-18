package com.aixuexi.androidperformace.net

class OkHttpEvent {
    var dnsStartTime: Long? = 0L
    var dnsEndTime: Long? = 0L
    var responseBodySize:Long?=0L
    var apiSuccess:Boolean?=false
    var errorReason:String?=""
}