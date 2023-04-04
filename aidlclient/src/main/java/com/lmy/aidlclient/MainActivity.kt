package com.lmy.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lmy.wanandroid.IPersonManager
import com.lmy.wanandroid.bean.Person

class MainActivity : AppCompatActivity() {
    private var remoteService: IPersonManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_bind).setOnClickListener {
            val intent = Intent()
            intent.action = "com.lmy.wanandroid.aidl.Server.Action" // action与服务端一致
            intent.setPackage("com.lmy.wanandroid")
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        findViewById<TextView>(R.id.tv_add).setOnClickListener {
            addPerson()
        }
        findViewById<TextView>(R.id.tv_get).setOnClickListener {
            getPerson()
        }
    }
    private var serviceConnection = object :ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            // 在onServiceConnected调用IPersonManager.Stub.asInterface获取接口类型的实例
            // 通过这个实例调用服务端的服务
            remoteService = IPersonManager.Stub.asInterface(service)
            Log.e("client", "onServiceConnected")
        }
    
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e("client", "onServiceDisconnected")
        }
    }
    
    var num = 3
    
    // 客户端新增Person
    private fun addPerson() {
        // 客户端调用服务端方法时,需要捕获以下几个异常:
        // RemoteException 异常：
        // DeadObjectException 异常：连接中断时会抛出异常；
        // SecurityException 异常：客户端和服务端中定义的 AIDL 发生冲突时会抛出异常；
        try {
            val addPersonResult = remoteService?.addPerson(Person("person$num"))
            num++
            Log.e("client", "addPerson result = $addPersonResult")
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
    
    private fun getPerson() {
        val personList = remoteService?.personList
        Log.e("client", "person 列表 $personList")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}