// IPersonManager.aidl
package com.lmy.wanandroid;

import com.lmy.wanandroid.bean.Person;

interface IPersonManager {
    List<Person> getPersonList();
    boolean addPerson(inout Person person);
}