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

    class ThreadEntity : Serializable {
        var tid: Long = 0
        var name: String? = null
        var priority: Int = 0
    }

    class ExceptionEntity : Serializable {
        var message: String? = null
        var stackTrace: ArrayList<String>? = null
    }
}