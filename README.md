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
```java
int seconds = 5;
double seconds = 4.5;
new Delay(seconds, new DelayCallback() {
    @Override
    public void onFinish() {
      // TODO action
    }
});

int minutes = 1;
double minutes = 1.5;
new Delay(minutes).action(new DelayCallback() {
    @Override
    public void onFinish() {
      // TODO action          
    }
});
```

### On Tick
```java
@Override
public void onTick(Times times) {
    long millis = times.getMillis();
    int seconds = times.getSeconds();
    int minutes = times.getMinutes();
    int hours = times.getHours();
    int days = times.getDays();
    Log.d("Countdown", "Seconds: "+seconds);
}
```
