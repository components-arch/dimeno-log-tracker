# dimeno-log-tracker

> AspectJ 实现操作日志与崩溃日志追踪

[![Platform](https://img.shields.io/badge/Platform-Android-00CC00.svg?style=flat)](https://developer.android.google.cn/)
[![](https://jitpack.io/v/dimeno-tech/dimeno-log-tracker.svg)](https://jitpack.io/#dimeno-tech/dimeno-log-tracker)

### 依赖导入

项目根目录

``` gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

模块目录

``` gradle
dependencies {
	implementation 'com.github.dimeno-tech:dimeno-log-tracker:0.0.1'
}
```

### 初始化

``` java
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Tracker.install(this, true)
    }
}
```

### 使用

对目标方法添加注解即可 **@Track**

``` java
@Track
fun onClick(view: View) {
	...
}
```

### 日志存储

操作日志

``` java
fun getTraceFile(): File {
    return File(Tracker.getContext()!!.getExternalFilesDir("tracks"), "track.log")
}
```

![](https://tva1.sinaimg.cn/large/0081Kckwgy1gks5j7qpxjj30qm0u0whs.jpg)

崩溃日志

``` java
fun getCrashFile(): File {
    return File(Tracker.getContext()!!.getExternalFilesDir("crash"), "crash.log")
}
```

![](https://tva1.sinaimg.cn/large/0081Kckwgy1gks5jjrq8bj30qe0u0gp4.jpg)





