Android Data Binding库自动生成的BR类，它包含了您在Data Binding中定义的所有变量的常量映射。

```kotlin
package androidx.databinding.library.baseAdapters;

public class BR {
    public static final int _all = 0;
    
    public static final int content = 1;
    
    public static final int item = 2;
    
    public static final int itemRadius = 3;
    
    public static final int letter = 4;
    
    public static final int vm = 5;
}
```

在这里面，_all是用于绑定所有变量的ID，其他ID则对应具体的绑定变量。在使用Data Binding时，需要将绑定变量的ID传递给setVariable()方法，以便将该变量绑定到布局中。

例如，如果你有一个包含以下内容的布局文件：

```kotlin
<layout>
    <data>
        <variable name="myText" type="String"/>
    </data>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{myText}"/>
</layout>
```

那么，可以像这样将数据传递给setVariable()方法来设置myText的值：

```kotlin
    MyLayoutBinding binding = DataBindingUtil.inflate(...);
    binding.setVariable(BR.myText, "Hello World!");
```
此时rebuild下可以看到BR中新生成了1个myText，也就是说在layout中我们导入的任意variable最终都会在BR文件生成一个常量。

所以在mvvm架构中，我们可以在BaseActivity里面通过调用setVariable绑定viewModel。

需要注意的是，setVariable传入的id和layout中variable的name属性要对应上（也就是如果setVariable传的id是BR.vm，那么在xml中导入的variable的name也要是vm）