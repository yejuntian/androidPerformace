package com.aixuexi.androidperformace.aop

import com.aixuexi.androidperformace.utils.LogUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
open class PerformanceAop {
    @Around("call(* com.aixuexi.androidperformace.PerformanceApp.**(..))")
    fun getTime(joinPoint: ProceedingJoinPoint){
        val time = System.currentTimeMillis()
        joinPoint.proceed()
        LogUtils.i("cost = ${System.currentTimeMillis() - time}")

    }
}