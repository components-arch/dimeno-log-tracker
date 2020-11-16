package com.dimeno.log.tracker.processor

import android.net.NetworkInfo
import android.os.Build
import android.widget.TextView
import com.dimeno.log.tracker.entity.CrashEntity
import com.dimeno.log.tracker.entity.TraceEntity
import com.dimeno.log.tracker.util.TrackerPath
import com.dimeno.log.tracker.util.Utils
import com.google.gson.Gson
import org.aspectj.lang.ProceedingJoinPoint
import java.io.PrintWriter
import java.io.RandomAccessFile
import java.io.StringWriter
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.*

/**
 * log writer
 * Created by wangzhen on 2020/11/14.
 */
class LogWriter {
    companion object {
        fun writeStackTraces(joinPoint: ProceedingJoinPoint) {
            // set date
            val entity = TraceEntity().apply {
                date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(
                    System.currentTimeMillis()
                )
                className = joinPoint.target?.javaClass?.name
                // set clicked widgets
                widgets = arrayListOf<TraceEntity.WidgetEntity>().apply {
                    for (arg in joinPoint.args) {
                        add(TraceEntity.WidgetEntity().apply {
                            widget = arg?.toString()
                            if (arg is TextView) {
                                text = arg.text.toString()
                            }
                        })
                    }
                }
                // set stack traces
                traces = arrayListOf<String>().apply {
                    Thread.currentThread().stackTrace.forEach { item ->
                        add("${item.className}.${item.methodName}():${item.lineNumber}")
                    }
                }
            }

            // write information to log file
            val file = TrackerPath.getTraceFile()
            val randomAccessFile = RandomAccessFile(file.absolutePath, "rw").apply {
                seek(length())
            }

            val builder = StringBuilder().apply {
                appendLine("#")
                appendLine(Gson().toJson(entity))
            }
            randomAccessFile.write(builder.toString().toByteArray())
            randomAccessFile.close()
        }

        fun writeCrash(t: Thread, e: Throwable) {
            val entity = CrashEntity().apply {
                date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(
                    System.currentTimeMillis()
                )
                thread = CrashEntity.ThreadEntity().apply {
                    tid = t.id
                    name = t.name
                    priority = t.priority
                }
                exception = CrashEntity.ExceptionEntity().apply {
                    if (e.cause is InvocationTargetException) {
                        exception =
                            (e.cause as InvocationTargetException).targetException?.javaClass?.name
                    }

                    message = e.message

                    val traces = arrayListOf<String>()
                    e.stackTrace.forEach { item ->
                        traces.add("${item.className}.${item.methodName}():${item.lineNumber}")
                    }
                    stackTrace = traces

                    val writer = StringWriter()
                    e.printStackTrace(PrintWriter(writer))
                    details = writer.toString()
                }
                app = CrashEntity.AppEntity().apply {
                    versionName = Utils.getVersionName()
                    versionCode = Utils.getVersionCode()
                }
                device = CrashEntity.DeviceEntity().apply {
                    id = Build.ID
                    manufacturer = Build.MANUFACTURER
                    brand = Build.BRAND
                    hardware = Build.HARDWARE
                    sdkInt = Build.VERSION.SDK_INT
                    release = Build.VERSION.RELEASE
                    supportedAbis =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            Build.SUPPORTED_ABIS
                        else
                            null
                    screenSize = "${Utils.getScreenHeight()}*${Utils.getScreenWidth()}"
                    Utils.getNetworkInfo()?.let { info ->
                        networkAvailable = info.state == NetworkInfo.State.CONNECTED
                        networkType = info.typeName
                    }
                }
            }

            val file = TrackerPath.getCrashFile()
            val randomAccessFile = RandomAccessFile(file.absolutePath, "rw").apply {
                seek(length())
            }

            val builder = StringBuilder().apply {
                appendLine("#")
                appendLine(Gson().toJson(entity))
            }
            randomAccessFile.write(builder.toString().toByteArray())
            randomAccessFile.close()
        }
    }
}