# Dagger.Android
首先上 [Dagger&Android](https://google.github.io/dagger/android) 官网链接。

### 为什么要使用 Dagger.Android？

**One of the central difficulties of writing an Android application using Dagger is that many Android framework classes are instantiated by the OS itself, like Activity and Fragment, but Dagger works best if it can create all the injected objects. Instead, you have to perform members injection in a lifecycle method. This means many classes end up looking like:**

```
public class FrombulationActivity extends Activity {
  @Inject Frombulator frombulator;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // DO THIS FIRST. Otherwise frombulator might be null!
    ((SomeApplicationBaseType) getContext().getApplicationContext())
        .getApplicationComponent()
        .newActivityComponentBuilder()
        .activity(this)
        .build()
        .inject(this);
    // ... now you can write the exciting code
  }
}
```
**This has a few problems:**

 1. Copy-pasting code makes it hard to refactor later on. As more and more developers copy-paste that block, fewer will know what it actually does.
 2. More fundamentally, it requires the type requesting injection (FrombulationActivity) to know about its injector. Even if this is done through interfaces instead of concrete types, it breaks a core principle of dependency injection: a class shouldn’t know anything about how it is injected.

> 以上是官网原话，巴拉巴拉的说为什么要使用 Dagger.Android 这个扩展库。按我的理解，大概意思是我们在使用 Dagger2 时，如果往 Activity 、Fragment 等由 Android 系统完成实例化的类中进行注解时，每次都需要创建一个 XXComponent，然后生成一个 DaggerXXComponent，再往 Activity 中注解， *DaggerXXComponent..builder().build().inject()*，每次都这样写，很不方便，于是  Dagger.Android 就诞生了。

### 如何配置（Kotlin）？

```
implementation 'com.google.dagger:dagger:2.16'
implementation 'com.google.dagger:dagger-android:2.16'
implementation 'com.google.dagger:dagger-android-support:2.16'

kapt 'com.google.dagger:dagger-compiler:2.16'
kapt 'com.google.dagger:dagger-android-processor:2.15'
```
> 对了，不要忘记加上 apply plugin: 'kotlin-kapt'

### 怎么用？

网上其他博客都已经说了怎么使用，我这里只说改进后的用法。

- 创建一个 MyApplication，继承 DaggerApplication 。

```
class MyApplication: DaggerApplication() {
    
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
```
> 不要忘记在 AndroidManifest.xml 文件中添加。
> 导入 dagger.android.support.DaggerApplication 包。

- 创建一个 AppModule （创不创建无所谓，我是为了演示，就创建了），这个里面呢，放一些常用的函数进去，比如我就放了一个 Log 打印。

```
@Module
class AppModule {

    @Provides
    fun provideLogUtil(): LogUtil = LogUtil()
}
```
> 这个 LogUtils 是我自己写的一个 Log 工具类，里面也没几行代码，就不放出来了。

- 创建一个 AppComponent

```
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
```
> 不要问为什么这么写，因为我也不清楚 - -

- 按 Ctrl+F9 ，然后修改 MyApplication 

```
 class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
```

以上就是基本配置，总结一下：

 1. 创建 Component
 2. 将这个 Component 放到我们自定义的 Application 中
 
下面就是具体用法。

#### Activity 中注入

- 创建 MainModule（我是在 MainActivity 中做演示）

```
@Module
class MainModule {

    @Provides
    fun provideString(): String = "This is Activity"
}
```
- 修改 MainActivity，继承 DaggerAppCompatActivity

```
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLog.i(value)
    }
}
```

- 创建一个抽象类 ActivityBindingModule ，这类的作用就是将 Activity 和它对应的 Module 绑定起来

```
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}
```

- 修改 AppComponent，将 ActivityBindingModule 添加到 modules 中

```
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
```

如果以后要添加新的 Activity，只需要：

 1. Activity 继承 DaggerAppCompatActivity
 2. 创建对应的 Module
 3. 在 ActivityBindingModule 中将 Activity 和 Module 进行绑定就好了

#### Fragment 中注入

- 创建 TestFragment，继承 DaggerFragment

```
class TestFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLog.i(value)
    }
}
```

> 
> 导入 import dagger.android.support.DaggerFragment
> 这里 TestFragment 的构造添加的 @Inject ，后面会在 MainActivity 中使用

- 创建 TestFragmentModule 

```
@Module
class TestFragmentModule {

    @Provides
    fun provideString(): String = "This is Fragment"
}
```

- 创建一个抽象类 FragmentBindingModule ，这类的作用就是将 Fragment 和它对应的 Module 绑定起来

```
@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [TestFragmentModule::class])
    abstract fun bindTestFragment(): TestFragment
}
```

> 这个类是不是看起来很熟悉？没错，我就是直接复制 ActivityBindingModule 然后稍微修改了一下。其实可以不用创建这个抽象类，而是直接放到 ActivityBindingModule 中直接用，不过为了看起来更舒服，我还是创建了。

- 修改 AppComponent，将 FragmentBindingModule 添加到 modules 中

```
 @Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    FragmentBindingModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
```

> 如果全程只用一个 XXXBindingModule 的话，就只用添加一次 XXXBindingModule 就好了。

- 修改 MainActivity ，将 TestFragment 添加进来 

```
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var value: String

    @Inject
    lateinit var mLog: LogUtil

    @Inject
    lateinit var mFragment: TestFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLog.i(value)

        supportFragmentManager.beginTransaction().add(R.id.fl, mFragment).commit()
    }
}
```

以后要添加 Fragment ，操作和上面添加 Activity 一样，不再赘述。

Service 、IntentService 、Broadcast 的注入和上面的都一样 ，我就不再写了，大家直接看项目就行了。

