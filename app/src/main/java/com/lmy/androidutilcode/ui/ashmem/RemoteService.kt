package com.lmy.androidutilcode.ui.ashmem

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.MemoryFile
import android.os.Parcel
import android.os.ParcelFileDescriptor
import java.io.FileDescriptor
import java.lang.Exception
import java.lang.reflect.Method

/**
 * @description：服务端
 *
 * 这个服务端中在远程调用的的时候，要做以下事情：
 *
 * 1.创建一个匿名共享内存
 * 2.往这个共享内存中写一个字符数据
 * 3.将这个匿名共享内存的文件句柄通过binder机制传递给客户端
 * (千万别忘了将service指定在另一个进程上)
 *
 * @author：mengyue.liu
 * @time： 2022/6/27 15:58
 */
class RemoteService : Service() {
    override fun onBind(p0: Intent?): IBinder {
        return MyBinder()
    }
    
    class MyBinder : Binder() {
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if (code == 1) {
                try {
                    val resultData = "this is result data"
                    val contentBytes = resultData.toByteArray() // 等价于java中String.getBytes()
                    // 创建匿名共享内存
                    val mf = MemoryFile("memfile", contentBytes.size)
                    // 往共享内存中写入字符数据
                    mf.writeBytes(contentBytes, 0, 0, contentBytes.size)
                    val method: Method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
                    //通过反射获得文件句柄
                    val fd: FileDescriptor = method.invoke(mf) as FileDescriptor
                    val pfd = ParcelFileDescriptor.dup(fd)
                    //将文件句柄写到binder调用的返回值中。
                    reply!!.writeFileDescriptor(fd)
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return super.onTransact(code, data, reply, flags)
        }
    }
}