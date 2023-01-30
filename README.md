# Count Down
This is a shortcut code for you to create a schedule timer and provide an action after the timer finishes running.

<img width="300px" src="https://user-images.githubusercontent.com/26743731/197709856-081bfab0-6e7f-40c9-8e14-6351125e2c62.gif"/>

## Installation

### repositories
```gradle
maven { url 'https://jitpack.io' }
```

### dependencies
```gradle
implementation 'com.github.eriffanani:CountDown:1.2.0'
```

## How to use
### Basic (Autostart)
* Java
```java
long duration = 1000L // 1 Seconds
int duration = 1 // 1 Seconds
new CountDown(duration, () -> {
    // TODO ACTION
});
```
* Kotlin
```kotlin
val duration = 4000L // 4 Seconds
val duration = 4 // 4 Seconds
CountDown(duration) {
    // TODO ACTION            
}
```

### CountDown Listener
* Java
```java
new CountDown(duration, new CountDownListener() {
    @Override
    public void onFinish() {}
    @Override
    public void onTick(Times times) {
        int days = times.getDays();
        int hours = times.getHours();
        int minutes = times.getMinutes();
        int seconds = times.getSeconds();
        long millis = times.getMillis();
    }
    @Override
    public void onPause() {}
    @Override
    public void onStop() {}
    @Override
    public void onResume() {}
});
```
* Kotlin
```kotlin
CountDown(duration, object : CountDownListener {
    override fun onFinish() {}
    override fun onTick(times: Times?) {
        val days = times?.daysStr()
        val hours = times?.hoursStr()
        val minutes = times?.minutesStr()
        val seconds = times?.secondsStr()
        val time = "$hours:$minutes:$seconds"
        textview.text = time
    }
    override fun onStop() {}
    override fun onPause() {}
    override fun onResume() {}
})
```

### With Action (Not autostart)
* Java
```java
CountDown.Builder countdown = new CountDown.Builder(duration, () -> {
    // TODO ACTION
});
countdown.start();
countdown.stop();
countdown.pause();
countdown.resume();
```
* Kotlin
```kotlin
val countdown = CountDown.Builder(duration) {
    // TODO ACTION
}
countdown.start()
```

### Custom Input
* Java
```java
long duration = CountDown.minutes(1); // 1 Minutes
long duration = CountDown.minutes(1.5); // 1:30 Minutes
long duration = CountDown.hour(1); // 1 Hours
long duration = CountDown.hour(1.5); // 1:30 Hours
new CountDown(duration, () -> {
    // TODO ACTION
});
```
* Kotlin
```kotlin
val date = "2022-10-25 14:00:00"
val format = "yyyy-MM-dd HH:mm:ss"
CountDown(date, format) {
    // TODO ACTION
}
```

#### Information
This library is still being developed further, please provide feedback if you find a bug. Thank you
### Licence
```license
Copyright 2022 Mukhammad Erif Fanani

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
