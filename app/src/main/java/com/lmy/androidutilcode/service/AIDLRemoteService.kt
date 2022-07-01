package com.lmy.androidutilcode.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.lmy.androidutilcode.IPersonManager
import com.lmy.androidutilcode.bean.Person

/**
 * @description：aidl服务端service
 * @author：mengyue.liu
 * @time： 2022/6/30 15:06
 */
class AIDLRemoteService : Service() {
    private val mPersonList = mutableListOf<Person?>()
    private val mBinder: Binder = object : IPersonManager.Stub() {
        override fun getPersonList(): MutableList<Person?> = mPersonList
        
        override fun addPerson(person: Person?): Boolean {
            return personList.add(person)
        }
    }
    
    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }
    
    override fun onCreate() {
        super.onCreate()
        mPersonList.add(Person("person1"))
        mPersonList.add(Person("person2"))
    }
}