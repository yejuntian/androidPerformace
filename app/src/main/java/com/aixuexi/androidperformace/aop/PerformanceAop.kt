package com.aixuexi.androidperformace.aop

import com.aixuexi.androidperformace.utils.LogUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * <类说明 必填>
 *
 * @author  tianyejun
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:12/12/20]
 */
@Aspect
class PerformanceAop {
    @Around("execution(* com.aixuexi.androidperformace.PerformanceApp.on**(..))")
    open fun getSetContentViewTime(joinPoint: ProceedingJoinPoint): Unit {
        val signature = joinPoint.signature
        val name = signature.toShortString()
        val time = System.currentTimeMillis()
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        LogUtils.e(name + " cost " + (System.currentTimeMillis() - time))
    }
}