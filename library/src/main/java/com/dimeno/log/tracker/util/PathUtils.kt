package com.dimeno.log.tracker.util

import com.dimeno.log.tracker.provider.TrackerContext
import java.io.File

/**
 * path utils
 * Created by wangzhen on 2020/11/13.
 */
class PathUtils {
    companion object {
        fun getLogFile(): File {
            return File(TrackerContext.sContext!!.getExternalFilesDir("tracks"), "track.log")
        }
    }
}