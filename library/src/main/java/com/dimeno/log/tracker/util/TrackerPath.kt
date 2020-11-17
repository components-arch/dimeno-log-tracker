package com.dimeno.log.tracker.util

import com.dimeno.log.tracker.Tracker
import java.io.File

/**
 * tracker path utils
 * Created by wangzhen on 2020/11/13.
 */
class TrackerPath {
    companion object {
        fun getTraceFile(): File {
            return File(Tracker.getContext()!!.getExternalFilesDir("tracks"), "track.log")
        }

        fun getCrashFile(): File {
            return File(Tracker.getContext()!!.getExternalFilesDir("crash"), "crash.log")
        }
    }
}