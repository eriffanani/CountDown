package com.erif.CountDown;

public interface CountDownListener {
    void onFinish();
    default void onTick(Times times){}
}
