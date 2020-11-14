package com.dimeno.log.tracker.processor

import android.widget.TextView
import com.dimeno.log.tracker.entity.CrashEntity
import com.dimeno.log.tracker.entity.TraceEntity
import com.dimeno.log.tracker.util.PathManager
import com.google.gson.Gson
import org.aspectj.lang.ProceedingJoinPoint
import java.io.RandomAccessFile
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
            }

            // set clicked widgets
            val widgets = arrayListOf<TraceEntity.WidgetEntity>()
            for (arg in joinPoint.args) {
                widgets.add(TraceEntity.WidgetEntity().apply {
                    widget = arg?.toString()
                    if (arg is TextView) {
                        text = arg.text.toString()
                    }
                })
            }
            entity.widgets = widgets

            // set stack traces
            val traces = arrayListOf<String>()
            Thread.currentThread().stackTrace.forEach { item ->
                traces.add("${item.className}#${item.methodName}():${item.lineNumber}")
            }
            entity.traces = traces

            // write information to log file
            val file = PathManager.getTraceFile()
            val randomAccessFile = RandomAccessFile(file.absolutePath, "rw").apply {
                seek(length())
            }

            val builder = StringBuilder().apply {
                appendLine("####################")
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
                    message = e.message
                    val traces = arrayListOf<String>()
                    e.stackTrace.forEach { item ->
                        traces.add("${item.className}#${item.methodName}():${item.lineNumber}")
                    }
                    stackTrace = traces
                }
            }

            val file = PathManager.getCrashFile()
            val randomAccessFile = RandomAccessFile(file.absolutePath, "rw").apply {
                seek(length())
            }

            val builder = StringBuilder().apply {
                appendLine("####################")
                appendLine(Gson().toJson(entity))
            }
            randomAccessFile.write(builder.toString().toByteArray())
            randomAccessFile.close()
        }
    }
}