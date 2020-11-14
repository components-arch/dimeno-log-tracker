package com.dimeno.log.tracker.util

import com.dimeno.log.tracker.Tracker
import java.io.File

/**
 * path utils
 * Created by wangzhen on 2020/11/13.
 */
class PathManager {
    companion object {
        fun getTraceFile(): File {
            return File(Tracker.sContext!!.getExternalFilesDir("tracks"), "track.log")
        }

        fun getCrashFile(): File {
            return File(Tracker.sContext!!.getExternalFilesDir("crash"), "crash.log")
        }
    }
}