package com.lmy.androidutilcode.ui.ashmem

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Parcel
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.base.BaseActivity
import com.lmy.androidutilcode.databinding.ActivityAshmemBinding
import com.lmy.androidutilcode.util.LogUtil
import java.io.BufferedReader
import java.io.FileDescriptor
import java.io.FileReader

/**
 * @description：匿名共享内存（Anonymous Shared Memory-Ashmem）  客户端
 * @author：mengyue.liu
 * @time： 2022/6/27 15:56
 */
class AshmemActivity : BaseActivity<ActivityAshmemBinding>() {
    
    override fun getLayoutId(): Int = R.layout.activity_ashmem
    
    override fun initData() {
        // 绑定服务
        val intent = Intent(this, RemoteService::class.java)
        bindService(
            intent,
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                    val data = Parcel.obtain() // 客户端数据
                    val reply = Parcel.obtain() // 服务端返回的数据
                    // 通过binder机制跨进程调用服务端的接口
                    service.transact(1, data, reply, 0)
                    // 获得RemoteService创建的匿名共享内存的fd
                    val fd: FileDescriptor = reply.readFileDescriptor().fileDescriptor
                    // 读取匿名共享内存中的数据，并打印log
                    val br = BufferedReader(FileReader(fd))
                    LogUtil.e(br.readLine())
                }
                
                override fun onServiceDisconnected(p0: ComponentName?) {}
            },
            Context.BIND_AUTO_CREATE)
    }
}