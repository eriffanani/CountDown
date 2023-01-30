package com.erif.CountDown;

public interface CountDownListener {
    void onFinish();
    default void onTick(Times times) {}
    default void onStop() {}
    default void onPause() {}
    default void onResume() {}
}
