# Count Down
This is a shortcut code for you to create a schedule timer and provide an action after the timer finishes running.

## Installation

### repositories
```gradle
maven { url 'https://jitpack.io' }
```

### dependencies
```gradle
implementation 'com.github.eriffanani:CountDown:1.1.0'
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
val duration = 1000L // Seconds
val duration = 1 // Seconds
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
