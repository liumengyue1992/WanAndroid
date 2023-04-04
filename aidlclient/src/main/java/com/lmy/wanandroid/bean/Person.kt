package com.lmy.wanandroid.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/30 14:32
 */
class Person(var name: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())
    
    override fun toString(): String {
        return "Person(name=$name) hashcode = ${hashCode()}"
    }
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }
    
    fun readFromParcel(parcel: Parcel) {
        this.name = parcel.readString()
    }
    
    override fun describeContents(): Int {
        return 0
    }
    
    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }
        
        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}