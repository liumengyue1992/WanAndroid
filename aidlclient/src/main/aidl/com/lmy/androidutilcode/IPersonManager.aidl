// IPersonManager.aidl
package com.lmy.androidutilcode;
// 导包
import com.lmy.androidutilcode.bean.Person;

interface IPersonManager {
    List<Person> getPersonList();
    boolean addPerson(in Person person);// in: 从客户端流向服务端
}