package com.example.fingerprint;

import android.app.Application;


public class GlobalVariable extends Application {

    private String fingerPrintEnable="No";
    private String lock="No";

    public GlobalVariable() {
    }

    public GlobalVariable(String fingerPrintEnable) {
        this.fingerPrintEnable = fingerPrintEnable;
    }

    public String getFingerPrintEnable() {
        return fingerPrintEnable;
    }

    public void setFingerPrintEnable(String fingerPrintEnable) {
        this.fingerPrintEnable = fingerPrintEnable;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }
}
