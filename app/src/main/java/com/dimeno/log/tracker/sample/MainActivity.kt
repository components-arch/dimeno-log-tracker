package com.dimeno.log.tracker.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dimeno.log.tracker.annotation.Track

/**
 * MainActivity
 * Created by wangzhen on 2020/11/12.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Track
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_hello -> {
                Log.e("TAG", "-> Hello World!")
            }
            R.id.btn_test -> {
                Log.e("TAG", "-> test")
            }
            R.id.btn_crash -> {
                throw NullPointerException("exception for test")
            }
        }
    }
}