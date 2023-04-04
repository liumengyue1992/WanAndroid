// IPersonManager.aidl
package com.lmy.wanandroid;
// 导包
import com.lmy.wanandroid.bean.Person;

interface IPersonManager {
    List<Person> getPersonList();
    boolean addPerson(in Person person);// in: 从客户端流向服务端
}