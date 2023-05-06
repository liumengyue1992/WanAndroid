package com.lmy.uitl

import android.util.Log

/**
 * 简单的日志工具
 */
object LogUtil {
    private var isDebug: Boolean = true
    private const val TAG = "LogUtil"
    
    /**
     * debug
     */
    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun dWithTag(tag: String,msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * debug
     */
    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }
    
    /**
     * error
     */
    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }
    
    /**
     * error
     */
    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }
    
    /**
     * warn
     */
    fun w(msg: String) {
        if (isDebug) {
            Log.w(TAG, msg)
        }
    }
    
    /**
     * warn
     */
    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }
    
    /**
     * info
     */
    fun i(msg: String) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }
    
    /**
     * info
     */
    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }
    
    /**
     * verbose
     */
    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }
    
    /**
     * verbose
     */
    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }
}