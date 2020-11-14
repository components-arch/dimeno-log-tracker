package com.dimeno.log.tracker.exception

import com.dimeno.log.tracker.processor.LogWriter

/**
 * tracker uncaught exception handler
 * Created by wangzhen on 2020/11/14.
 */
class TrackerExceptionHandler : Thread.UncaughtExceptionHandler {
    var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    init {
        if (Thread.getDefaultUncaughtExceptionHandler() !is TrackerExceptionHandler) {
            defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(this)
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        LogWriter.writeCrash(t,e)
        defaultExceptionHandler?.uncaughtException(t, e)
    }
}