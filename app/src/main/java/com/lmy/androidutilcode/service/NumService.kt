package com.lmy.androidutilcode.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lmy.uitl.ToastUtil

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/20 10:19
 */
class NumService : Service() {
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ToastUtil.showShort("启动了")
        var num = 1
        val thread = object : Thread("num") {
            override fun run() {
                super.run()
                while (true){
                    sleep(100)
                    Log.e("lmy", "num = $num")
                    num++
                }
            }
        }
        thread.start()
        return super.onStartCommand(intent, flags, startId)
    }
    
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}