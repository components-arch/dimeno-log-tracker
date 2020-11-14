package com.dimeno.log.tracker.sample

import android.app.Application
import com.dimeno.log.tracker.Tracker

/**
 * base application
 * Created by wangzhen on 2020/11/14.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Tracker.install(this, true)
    }
}