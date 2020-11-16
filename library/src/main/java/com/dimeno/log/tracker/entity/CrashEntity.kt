package com.dimeno.log.tracker.entity

import java.io.Serializable

/**
 * crash entity
 * Created by wangzhen on 2020/11/14.
 */
class CrashEntity : Serializable {
    var date: String? = null
    var thread: ThreadEntity? = null
    var exception: ExceptionEntity? = null
    var app: AppEntity? = null
    var device: DeviceEntity? = null

    class ThreadEntity : Serializable {
        var tid: Long = 0
        var name: String? = null
        var priority: Int = 0
    }

    class ExceptionEntity : Serializable {
        var message: String? = null
        var stackTrace: ArrayList<String>? = null
        var details: String? = null
    }

    class AppEntity : Serializable {
        var versionName: String? = null
        var versionCode: Long = 0
    }

    class DeviceEntity : Serializable {
        var id: String? = null
        var manufacturer: String? = null
        var brand: String? = null
        var hardware: String? = null
        var sdkInt: Int = 0
        var release: String? = null
        var supportedAbis: Array<out String>? = null
        var screenSize: String? = null
        var networkAvailable: Boolean = false
        var networkType: String? = null
    }
}