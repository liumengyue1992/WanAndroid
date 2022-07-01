// IPersonManager.aidl
package com.lmy.androidutilcode;

import com.lmy.androidutilcode.bean.Person;

interface IPersonManager {
    List<Person> getPersonList();
    boolean addPerson(inout Person person);
}