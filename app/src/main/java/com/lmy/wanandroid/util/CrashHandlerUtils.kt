package com.lmy.uitl

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.util.Log
import com.lmy.wanandroid.MyApplication
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.system.exitProcess

/**
 * @description：UncaughtException处理类，当程序发生Uncaught异常的时候，由该类来接管程序，并记录发送错误报告.
 * @author：Mengyue.Liu
 * @time： 2022/5/20 17:02
 */
class CrashHandlerUtils private constructor() : Thread.UncaughtExceptionHandler {
    companion object {
        const val tag = "XCrashHandlerUtils"
        
        val instance: CrashHandlerUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandlerUtils()
        }
        
        /**
         * 异常日志 存储位置为根目录下的 Crash文件夹
         */
        val path = MyApplication.mContext.getExternalFilesDir("crash").toString()
        
        /**
         * 文件名
         */
        private const val file_name = "crash_"
        
        /**
         * 文件名后缀
         */
        private const val file_name_suffix = ".txt"
    }
    
    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    
    private var mContext: Context? = null
    
    /**
     * 初始化
     *
     * @param context 上下文
     */
    fun init(context: Context?) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
    
    /**
     * 当系统中有未被捕获的异常，系统将会自动调用 uncaughtException 方法
     *
     * @param thread 出现未捕获异常的线程
     * @param ex     未捕获的异常
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        // 导入异常信息到SD卡中
        try {
            dumpExceptionToSDCard(ex)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // 这里可以上传异常信息到服务器
        // uploadExceptionToServer()
        ex.printStackTrace()
        // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }
    
    /**
     * 将异常信息写入txt
     * @param e
     */
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun dumpExceptionToSDCard(e: Throwable) {
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
        val file = File(path, file_name + time + file_name_suffix)
        try {
            val pw = PrintWriter(BufferedWriter(FileWriter(file)))
            pw.println(time)
            dumpPhoneInfo(pw)
            pw.println()
            e.printStackTrace(pw)
            pw.close()
        } catch (e1: Exception) {
            Log.e(tag, "dump crash info failed")
        }
    }
    
    /**
     * 获取手机各项信息
     *
     * @param pw
     */
    @Throws(PackageManager.NameNotFoundException::class)
    private fun dumpPhoneInfo(pw: PrintWriter) {
        //得到包管理器
        val pm = mContext!!.packageManager
        //得到包对象
        val pi = pm.getPackageInfo(mContext!!.packageName, PackageManager.GET_ACTIVITIES)
        //写入APP版本号
        pw.print("App Version: ")
        pw.print(pi.versionName)
        pw.print("_")
        pw.println(pi.versionCode)
        //写入 Android 版本号
        pw.print("OS Version: ")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)
        //手机制造商
        pw.print("Vendor: ")
        pw.println(Build.MANUFACTURER)
        //手机型号
        pw.print("Model: ")
        pw.println(Build.MODEL)
        //CPU架构
        pw.print("CPU ABI: ")
        pw.println(Build.SUPPORTED_ABIS)
    }
}