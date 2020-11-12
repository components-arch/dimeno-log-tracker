package com.dimeno.log.tracker

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * track processor
 * Created by wangzhen on 2020/11/12.
 */
@Aspect
class TrackProcessor {
    @Pointcut(ANNOTATION)
    fun annotation() {
    }

    @Around("annotation()")
    @Throws(Throwable::class)
    fun process(joinPoint: ProceedingJoinPoint) {
        Log.e("TAG", "-> onClick process")
        joinPoint.proceed()
    }

    companion object {
        private const val ANNOTATION = "execution(@com.dimeno.log.tracker.annotation.Track * *(..))"
    }
}