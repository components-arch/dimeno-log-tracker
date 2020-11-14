package com.dimeno.log.tracker

import android.content.Context
import com.dimeno.log.tracker.exception.TrackerExceptionHandler

/**
 * tracker
 * Created by wangzhen on 2020/11/14.
 */
class Tracker {
    companion object {
        var sContext: Context? = null
        fun install(context: Context) {
            install(context, false)
        }

        fun install(context: Context, registerExceptionHandler: Boolean) {
            sContext = context
            if (registerExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(TrackerExceptionHandler())
            }
        }
    }
}