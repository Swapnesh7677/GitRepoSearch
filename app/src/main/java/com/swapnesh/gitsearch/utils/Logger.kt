package com.swapnesh.gitsearch.util.jflibs

import android.util.Log
import com.swapnesh.gitsearch.BuildConfig


@Suppress("unused")
object Logger {
    @JvmStatic
    fun d(tag: String?, value: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, value!!)
        }
    }

    fun i(tag: String?, value: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, value!!)
        }
    }

    fun logApi(tag: String?, api_name: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, "$api_name => $msg")
        }
    }
}