package com.dimeno.log.tracker

import android.widget.TextView
import com.dimeno.log.tracker.entity.LogEntity
import com.dimeno.log.tracker.util.PathUtils
import com.google.gson.Gson
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.*

/**
 * track processor
 * Created by wangzhen on 2020/11/12.
 */
@Aspect
class TrackProcessor {
    companion object {
        private const val ANNOTATION = "execution(@com.dimeno.log.tracker.annotation.Track * *(..))"
    }

    @Pointcut(ANNOTATION)
    fun annotation() {
    }

    @Around("annotation()")
    @Throws(Throwable::class)
    fun process(joinPoint: ProceedingJoinPoint) {
        track(joinPoint)
        joinPoint.proceed()
    }

    private fun track(joinPoint: ProceedingJoinPoint) {
        // set date
        val entity = LogEntity().apply {
            date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(
                System.currentTimeMillis()
            )
        }

        // set clicked widgets
        val widgets = arrayListOf<LogEntity.WidgetEntity>()
        for (arg in joinPoint.args) {
            widgets.add(LogEntity.WidgetEntity().apply {
                widget = arg.toString()
                if (arg is TextView) {
                    text = arg.text.toString()
                }
            })
        }
        entity.widgets = widgets

        // set stack traces
        val traces = arrayListOf<String>()
        Thread.currentThread().stackTrace.let {
            it.forEach { item ->
                traces.add("${item.className}#${item.methodName}():${item.lineNumber}")
            }
        }
        entity.traces = traces

        // write information to log file
        val file = PathUtils.getLogFile()
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