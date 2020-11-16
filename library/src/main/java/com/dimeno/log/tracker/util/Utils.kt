package com.dimeno.log.tracker.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import com.dimeno.log.tracker.Tracker

/**
 * Utils
 * Created by wangzhen on 2020/11/16.
 */
class Utils {
    companion object {
        fun getVersionName(): String {
            Tracker.sContext?.let { context ->
                return context.packageManager.getPackageInfo(context.packageName, 0).versionName
            }
            return "1.0.0"
        }

        fun getVersionCode(): Long {
            Tracker.sContext?.let { context ->
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    packageInfo.versionCode.toLong()
                }
            }
            return 0
        }

        fun getScreenWidth(): Int {
            Tracker.sContext?.let { context ->
                return context.resources.displayMetrics.widthPixels
            }
            return 0
        }

        fun getScreenHeight(): Int {
            Tracker.sContext?.let { context ->
                return context.resources.displayMetrics.heightPixels
            }
            return 0
        }

        fun getNetworkInfo(): NetworkInfo? {
            Tracker.sContext?.let { context ->
                return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            }
            return null
        }
    }
}