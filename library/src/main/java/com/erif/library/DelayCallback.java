package com.erif.library;

public interface DelayCallback {
    void onFinish();
    default void onTick(Times times){}
}
